package com.bareuni.bareuniv2.domain.community.dto;

import java.util.ArrayList;
import java.util.List;

import com.bareuni.coredomain.domain.community.Community;

import jakarta.validation.constraints.NotBlank;

public record CreateCommunityRequest(

	@NotBlank(message = "[ERROR] 제목 입력은 필수입니다.")
	String title,
	@NotBlank(message = "[ERROR] 내용 입력은 필수입니다.")
	String content,
	List<String> imageUrls
) {
	public Community toEntity() {
		return Community.builder()
			.tile(title)
			.content(content)
			.user(null)
			.communityImages(new ArrayList<>())
			.comments(new ArrayList<>())
			.build();
	}
}
