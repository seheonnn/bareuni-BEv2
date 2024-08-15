package com.bareuni.bareuniv2.auth.dto;

import java.util.Optional;

import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
	Long id,
	String username,
	String email,
	String profileUrl
) {

	public static UserRegisterResponse from(User user) {
		return UserRegisterResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.profileUrl(
				Optional.ofNullable(user.getUserImage())
					.map(UserImage::getUrl)
					.orElse(null))
			.build();
	}
}
