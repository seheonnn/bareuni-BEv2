package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;

import lombok.Builder;

@Builder
public record UpdateCommunityResponse(
	Long id,
	String title,
	String content,
	List<String> imageUrls
) {
	public static UpdateCommunityResponse from(Community community) {
		List<String> imageUrls = community.getCommunityImages().stream()
			.map(CommunityImage::getUrl)
			.toList();

		return UpdateCommunityResponse.builder()
			.id(community.getId())
			.title(community.getTile())
			.content(community.getContent())
			.imageUrls(imageUrls)
			.build();
	}
}
