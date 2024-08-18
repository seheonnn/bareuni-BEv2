package com.bareuni.bareuniv2.domain.community.converter;

import com.bareuni.coredomain.domain.community.CommunityImage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityImageConverter {

	public static CommunityImage toEntity(String url, int order) {
		return CommunityImage.builder()
			.url(url)
			.imageOrder(order)
			.build();
	}
}
