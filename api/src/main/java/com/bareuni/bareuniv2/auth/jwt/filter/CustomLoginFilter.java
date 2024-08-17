package com.bareuni.bareuniv2.auth.jwt.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bareuni.bareuniv2.auth.jwt.dto.JwtDto;
import com.bareuni.bareuniv2.auth.jwt.exception.SecurityErrorCode;
import com.bareuni.bareuniv2.auth.jwt.userdetails.CustomUserDetails;
import com.bareuni.bareuniv2.auth.jwt.util.HttpResponseUtil;
import com.bareuni.bareuniv2.auth.jwt.util.JwtUtil;
import com.bareuni.coredomain.global.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response
	) throws AuthenticationException {
		log.info("[*] Login Filter");

		Map<String, Object> requestBody;
		try {
			requestBody = getBody(request);
		} catch (IOException e) {
			throw new AuthenticationServiceException("Error of request body.");
		}

		log.info("[*] Request Body : " + requestBody);

		String email = (String)requestBody.get("email");
		String password = (String)requestBody.get("password");

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password,
			null);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain chain,
		@NonNull Authentication authentication) throws IOException {
		log.info("[*] Login Success");

		CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

		log.info("[*] Login with " + customUserDetails.getUsername());

		JwtDto jwtDto = new JwtDto(
			jwtUtil.createJwtAccessToken(customUserDetails),
			jwtUtil.createJwtRefreshToken(customUserDetails)
		);

		HttpResponseUtil.setSuccessResponse(response, HttpStatus.CREATED, jwtDto);
	}

	@Override
	protected void unsuccessfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull AuthenticationException failed) throws IOException {

		log.info("[*] Login Fail");

		SecurityErrorCode errorCode;
		if (failed instanceof BadCredentialsException) {
			errorCode = SecurityErrorCode.BAD_CREDENTIALS;
		} else if (failed instanceof LockedException || failed instanceof DisabledException) {
			errorCode = SecurityErrorCode.FORBIDDEN;
		} else if (failed instanceof UsernameNotFoundException) {
			errorCode = SecurityErrorCode.USER_NOT_FOUND;
		} else if (failed instanceof AuthenticationServiceException) {
			errorCode = SecurityErrorCode.INTERNAL_SECURITY_ERROR;
		} else {
			errorCode = SecurityErrorCode.UNAUTHORIZED;
		}
		HttpResponseUtil.setErrorResponse(
			response,
			errorCode.getHttpStatus(),
			ApiResponse.onFailure(
				errorCode.getCode(),
				errorCode.getMessage()
			)
		);
	}

	private Map<String, Object> getBody(HttpServletRequest request) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		String line;

		try (BufferedReader bufferedReader = request.getReader()) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		String requestBody = stringBuilder.toString();
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(requestBody, Map.class);
	}
}

