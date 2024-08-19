package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

public record UpdateCommunityRequest(
	String content,
	List<String> imageUrls
) {
}
