package com.bareuni.bareuniv2.domain.community.dto;

import com.bareuni.coredomain.domain.comment.Comment;

import lombok.Builder;

@Builder
public record UpdateCommentResponse(
	Long id,
	String content
) {
	public static CreateCommentResponse from(Comment comment) {

		return CreateCommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.build();
	}
}
