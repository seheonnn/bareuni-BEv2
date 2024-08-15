package com.bareuni.bareuniv2.domain.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.domain.user.dto.UploadProfileImageResponse;
import com.bareuni.bareuniv2.domain.user.service.UserService;
import com.bareuni.coredomain.global.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/user")
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/upload-profile")
	public ApiResponse<UploadProfileImageResponse> uploadProfileImage(
		@RequestPart(name = "file") MultipartFile file) {
		return ApiResponse.onSuccess(userService.uploadProfileImage(file));
	}
}
