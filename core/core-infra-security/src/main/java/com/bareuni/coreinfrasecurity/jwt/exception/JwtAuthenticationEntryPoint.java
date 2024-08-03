package com.bareuni.coreinfrasecurity.jwt.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coreinfrasecurity.jwt.util.HttpResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException)
		throws IOException {
		HttpStatus httpStatus;
		ApiResponse<String> errorResponse;

		log.error("[*] AuthenticationException: ", authException);
		httpStatus = HttpStatus.UNAUTHORIZED;
		errorResponse = ApiResponse.onFailure(
			SecurityErrorCode.INVALID_TOKEN.getCode(),
			SecurityErrorCode.INVALID_TOKEN.getMessage(),
			authException.getMessage());

		HttpResponseUtil.setErrorResponse(response, httpStatus, errorResponse);
	}
}
