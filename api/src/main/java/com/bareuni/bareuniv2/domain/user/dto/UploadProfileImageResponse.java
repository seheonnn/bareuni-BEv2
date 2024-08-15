package com.bareuni.bareuniv2.domain.user.dto;

import com.bareuni.coredomain.domain.user.UserImage;

import lombok.Builder;

@Builder
public record UploadProfileImageResponse(
	String profileUrl
) {
	public static UploadProfileImageResponse from(UserImage userImage) {
		return UploadProfileImageResponse.builder()
			.profileUrl(userImage.getUrl())
			.build();
	}
}
