package com.bareuni.bareuniv2.domain.comment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentRequest;
import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentResponse;
import com.bareuni.bareuniv2.domain.comment.dto.UpdateCommentRequest;
import com.bareuni.bareuniv2.domain.comment.service.CommentService;
import com.bareuni.bareuniv2.domain.facade.CommunityCommentFacade;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v2/community/{communityId}/comments")
@RestController
public class CommentController {

	private final CommentService commentService;
	private final CommunityCommentFacade communityCommentFacade;

	@PostMapping("/create")
	public ApiResponse<CreateCommentResponse> createComment(
		@UserResolver User user,
		@PathVariable Long communityId,
		@RequestBody CreateCommentRequest request
	) {
		return ApiResponse.onSuccess(communityCommentFacade.createComment(communityId, user, request));
	}

	@PostMapping("/update/{id}")
	public ApiResponse<CreateCommentResponse> updateComment(
		@UserResolver User user,
		@PathVariable Long communityId,
		@RequestBody UpdateCommentRequest request,
		@PathVariable Long id
	) {
		return ApiResponse.onSuccess(commentService.updateComment(communityId, user, request, id));
	}
}
