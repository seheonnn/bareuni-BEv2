package com.bareuni.bareuniv2.auth.dto;

import com.bareuni.coredomain.domain.user.User;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
	Long id,
	String username,
	String email
) {

	public static UserRegisterResponse from(User user) {
		return UserRegisterResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.build();
	}
}
