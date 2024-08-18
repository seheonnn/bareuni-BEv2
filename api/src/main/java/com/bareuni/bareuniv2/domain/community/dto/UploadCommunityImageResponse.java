package com.bareuni.bareuniv2.domain.community.dto;

import com.bareuni.coredomain.domain.community.CommunityImage;

import lombok.Builder;

@Builder
public record UploadCommunityImageResponse(
	String profileUrl,
	int order
) {
	public static UploadCommunityImageResponse from(CommunityImage communityImage) {
		return UploadCommunityImageResponse.builder()
			.profileUrl(communityImage.getUrl())
			.order(communityImage.getImageOrder())
			.build();
	}
}
