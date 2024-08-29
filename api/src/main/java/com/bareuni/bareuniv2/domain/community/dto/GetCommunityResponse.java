package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

import com.bareuni.bareuniv2.domain.user.dto.UserSummary;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;

import lombok.Builder;

@Builder
public record GetCommunityResponse(

	Long id,
	String content,
	UserSummary user,
	List<String> imageUrls,
	long commentCnt
) {
	public static GetCommunityResponse of(Community community, Long commentCnt) {
		List<String> imageUrls = community.getCommunityImages().stream()
			.map(CommunityImage::getUrl)
			.toList();

		return GetCommunityResponse.builder()
			.id(community.getId())
			.content(community.getContent())
			.user(UserSummary.from(community.getUser()))
			.imageUrls(imageUrls)
			.commentCnt(commentCnt)
			.build();
	}
}
