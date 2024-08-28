package com.bareuni.bareuniv2.domain.user.dto;

import com.bareuni.coredomain.domain.user.User;

import lombok.Builder;

@Builder
public record UserSummary(
	String username,
	String profileUrl
) {
	public static UserSummary from(User user) {
		String url = user.getUserImage() == null ? null : user.getUserImage().getUrl();
		return UserSummary.builder()
			.username(user.getUsername())
			.profileUrl(url)
			.build();
	}
}
