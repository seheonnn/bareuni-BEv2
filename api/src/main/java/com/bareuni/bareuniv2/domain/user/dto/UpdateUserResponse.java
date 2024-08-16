package com.bareuni.bareuniv2.domain.user.dto;

import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.User;

import lombok.Builder;

@Builder
public record UpdateUserResponse(

	String username,
	String phone,
	GenderType gender,
	Integer age,
	Boolean ortho,
	String profileUrl
) {
	public static UpdateUserResponse from(User user) {
		return UpdateUserResponse.builder()
			.username(user.getUsername())
			.phone(user.getPhone())
			.gender(user.getGender())
			.age(user.getAge())
			.ortho(user.isOrtho())
			.profileUrl(user.getUserImage().getUrl())
			.build();
	}
}
