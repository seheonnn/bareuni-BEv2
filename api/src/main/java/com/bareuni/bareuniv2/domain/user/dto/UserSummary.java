package com.bareuni.bareuniv2.domain.user.dto;

import com.bareuni.coredomain.domain.user.User;

import lombok.Builder;

@Builder
public record UserSummary(
	String username,
	String profileUrl
) {
	public static UserSummary from(User user) {
		return UserSummary.builder()
			.username(user.getUsername())
			.profileUrl(user.getUserImage().getUrl())
			.build();
	}
}
