package com.bareuni.bareuniv2.domain.community.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommentRequest;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommentResponse;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityResponse;
import com.bareuni.bareuniv2.domain.community.dto.GetCommunitiesResponse;
import com.bareuni.bareuniv2.domain.community.dto.UpdateCommentRequest;
import com.bareuni.bareuniv2.domain.community.dto.UpdateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.dto.UpdateCommunityResponse;
import com.bareuni.bareuniv2.domain.community.dto.UploadCommunityImageResponse;
import com.bareuni.bareuniv2.domain.community.service.CommunityQueryService;
import com.bareuni.bareuniv2.domain.community.service.CommunityService;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/community")
@RestController
public class CommunityController {

	private final CommunityService communityService;
	private final CommunityQueryService communityQueryService;

	@PostMapping(value = "/upload-image/{order}", consumes = "multipart/form-data")
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

	@PostMapping(value = "/create-origin", consumes = "multipart/form-data")
	public ApiResponse<CreateCommunityResponse> createCommunityOrigin(
		@UserResolver User user,
		@RequestPart MultipartFile image,
		@RequestPart @Valid CreateCommunityRequest request
	) {
		return ApiResponse.onSuccess(communityService.createCommunityOrigin(user, image, request));
	}

	@PatchMapping("/update/{id}")
	public ApiResponse<UpdateCommunityResponse> updateCommunity(
		@UserResolver User user,
		@PathVariable Long id,
		@RequestBody UpdateCommunityRequest request
	) {
		return ApiResponse.onSuccess(communityService.updateCommunity(id, user, request));
	}

	@DeleteMapping("/delete/{id}")
	public ApiResponse<String> deleteCommunity(
		@UserResolver User user,
		@PathVariable Long id
	) {
		return ApiResponse.onSuccess(communityService.deleteCommunity(id, user));
	}

	@GetMapping("/read")
	public ApiResponse<PageResponse<GetCommunitiesResponse>> getCommunities(
		@ModelAttribute @Valid PageCondition pageCondition
	) {
		return ApiResponse.onSuccess(communityQueryService.getCommunities(pageCondition));
	}

	// @GetMapping("/read/{id}")
	// public ApiResponse<GetCommunitiesResponse> getCommunity(
	// 	@PathVariable Long id
	// ) {
	// 	return ApiResponse.onSuccess(communityQueryService.getCommunities(pageCondition));
	// }

	@PostMapping("/{id}/comment/create")
	public ApiResponse<CreateCommentResponse> createComment(
		@UserResolver User user,
		@PathVariable Long id,
		@RequestBody CreateCommentRequest request
	) {
		return ApiResponse.onSuccess(communityService.createComment(id, user, request));
	}

	@PostMapping("/{id}/comment/update/{commentId}")
	public ApiResponse<CreateCommentResponse> updateComment(
		@UserResolver User user,
		@PathVariable Long id,
		@RequestBody UpdateCommentRequest request,
		@PathVariable Long commentId
	) {
		return ApiResponse.onSuccess(communityService.updateComment(id, user, request, commentId));
	}
}
