package com.bareuni.bareuniv2.domain.community.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityResponse;
import com.bareuni.bareuniv2.domain.community.dto.UploadCommunityImageResponse;
import com.bareuni.bareuniv2.domain.community.service.CommunityService;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/community")
@RestController
public class CommunityController {

	private final CommunityService communityService;

	@PostMapping("/upload-image/{order}")
	public ApiResponse<UploadCommunityImageResponse> uploadCommunityImage(
		@PathVariable int order,
		@RequestPart(name = "file") MultipartFile file) {
		return ApiResponse.onSuccess(communityService.uploadCommunityImage(file, order));
	}

	@PostMapping("/create")
	public ApiResponse<CreateCommunityResponse> createCommunity(
		@UserResolver User user,
		@RequestBody @Valid CreateCommunityRequest request
	) {
		return ApiResponse.onSuccess(communityService.createCommunity(user, request));
	}
}
