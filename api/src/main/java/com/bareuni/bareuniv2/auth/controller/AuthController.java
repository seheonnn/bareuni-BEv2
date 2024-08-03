package com.bareuni.bareuniv2.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bareuni.bareuniv2.auth.dto.UserRegisterRequest;
import com.bareuni.bareuniv2.auth.dto.UserRegisterResponse;
import com.bareuni.bareuniv2.auth.service.AuthService;
import com.bareuni.coredomain.domain.user.entity.User;
import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coreinfrasecurity.annotation.UserResolver;
import com.bareuni.coreinfrasecurity.jwt.dto.JwtDto;

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
	public ApiResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
		return ApiResponse.onSuccess(authService.register(request));
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
