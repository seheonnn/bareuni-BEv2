package com.bareuni.bareuniv2.domain.user.converter;

import com.bareuni.coredomain.domain.user.UserImage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

	public static UserImage toUserImage(String url) {
		return UserImage.builder()
			.url(url)
			.build();
	}
}
