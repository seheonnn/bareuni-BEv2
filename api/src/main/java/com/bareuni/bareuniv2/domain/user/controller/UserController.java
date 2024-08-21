package com.bareuni.bareuniv2.domain.user.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.bareuniv2.domain.user.dto.UpdateUserRequest;
import com.bareuni.bareuniv2.domain.user.dto.UpdateUserResponse;
import com.bareuni.bareuniv2.domain.user.dto.UploadProfileImageResponse;
import com.bareuni.bareuniv2.domain.user.service.UserService;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/user")
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping(value = "/upload-profile", consumes = "multipart/form-data")
	public ApiResponse<UploadProfileImageResponse> uploadProfileImage(
		@RequestPart(name = "file") MultipartFile file) {
		return ApiResponse.onSuccess(userService.uploadProfileImage(file));
	}

	@PatchMapping("/update")
	public ApiResponse<UpdateUserResponse> updateUser(
		@UserResolver User user,
		@RequestBody @Valid UpdateUserRequest updateUserRequest
	) {
		return ApiResponse.onSuccess(userService.updateUser(user, updateUserRequest));
	}
}
