package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;

import lombok.Builder;

@Builder
public record CreateCommunityResponse(
	Long id,
	String content,
	List<String> imageUrls
) {
	public static CreateCommunityResponse from(Community community) {
		List<String> imageUrls = community.getCommunityImages().stream()
			.map(CommunityImage::getUrl)
			.toList();

		return CreateCommunityResponse.builder()
			.id(community.getId())
			.content(community.getContent())
			.imageUrls(imageUrls)
			.build();
	}
}
