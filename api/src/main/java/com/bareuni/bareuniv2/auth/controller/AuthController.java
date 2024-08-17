package com.bareuni.bareuniv2.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.bareuniv2.auth.dto.JoinUserRequest;
import com.bareuni.bareuniv2.auth.dto.JoinUserResponse;
import com.bareuni.bareuniv2.auth.jwt.dto.JwtDto;
import com.bareuni.bareuniv2.auth.service.AuthService;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
@RestController
public class AuthController {

	private final AuthService authService;

	@PostMapping("/join")
	public ApiResponse<JoinUserResponse> join(@Valid @RequestBody JoinUserRequest request) {
		return ApiResponse.onSuccess(authService.join(request));
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		return ApiResponse.onSuccess(authService.reissueToken(refreshToken));
	}

	@GetMapping("/test")
	public ApiResponse<String> register(@UserResolver User user) {
		return ApiResponse.onSuccess(user.getUsername());
	}
}
