package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.user.User;

import lombok.Builder;

@Builder
public record GetCommunityResponse(

	Long id,
	String content,
	User user,
	List<String> imageUrls
) {
	public static GetCommunityResponse from(Community community) {
		List<String> imageUrls = community.getCommunityImages().stream()
			.map(CommunityImage::getUrl)
			.toList();

		return GetCommunityResponse.builder()
			.id(community.getId())
			.content(community.getContent())
			.user(community.getUser())
			.imageUrls(imageUrls)
			.build();
	}
}
