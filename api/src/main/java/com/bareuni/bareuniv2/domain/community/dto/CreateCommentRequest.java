package com.bareuni.bareuniv2.domain.community.dto;

import com.bareuni.coredomain.domain.comment.Comment;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(

	@NotBlank(message = "[ERROR] 내용 입력은 필수입니다.")
	String content
) {

	public Comment toEntity() {
		return Comment.builder()
			.content(content)
			.user(null)
			.community(null)
			.build();

	}
}
