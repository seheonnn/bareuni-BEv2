package com.bareuni.bareuniv2.domain.comment.dto;

import com.bareuni.bareuniv2.domain.user.dto.UserSummary;
import com.bareuni.coredomain.domain.comment.Comment;

import lombok.Builder;

@Builder
public record GetCommentsResponse(
	Long id,
	String content,
	UserSummary user
) {
	public static GetCommentsResponse from(Comment comment) {

		return GetCommentsResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.user(UserSummary.from(comment.getUser()))
			.build();
	}
}
