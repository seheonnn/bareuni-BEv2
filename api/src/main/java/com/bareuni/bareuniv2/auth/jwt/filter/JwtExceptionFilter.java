package com.bareuni.bareuniv2.auth.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bareuni.bareuniv2.auth.jwt.exception.SecurityCustomException;
import com.bareuni.bareuniv2.auth.jwt.exception.SecurityErrorCode;
import com.bareuni.bareuniv2.auth.jwt.util.HttpResponseUtil;
import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coredomain.global.BaseErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (SecurityCustomException e) {
			log.warn("[*] SecurityCustomException : ", e);
			BaseErrorCode errorCode = e.getErrorCode();
			ApiResponse<String> errorResponse = ApiResponse.onFailure(
				errorCode.getCode(),
				errorCode.getMessage(),
				e.getMessage()
			);
			HttpResponseUtil.setErrorResponse(
				response,
				errorCode.getHttpStatus(),
				errorResponse
			);
		} catch (Exception e) {
			log.error("[*] Exception : ", e);
			ApiResponse<String> errorResponse = ApiResponse.onFailure(
				SecurityErrorCode.INTERNAL_SECURITY_ERROR.getCode(),
				SecurityErrorCode.INTERNAL_SECURITY_ERROR.getMessage(),
				e.getMessage()
			);
			HttpResponseUtil.setErrorResponse(
				response,
				HttpStatus.INTERNAL_SERVER_ERROR,
				errorResponse
			);
		}
	}
}
