package com.bareuni.bareuniv2.domain.community.dto;

import com.bareuni.bareuniv2.domain.user.dto.UserSummary;
import com.bareuni.coredomain.domain.community.Community;

import lombok.Builder;

@Builder
public record GetCommunitiesResponse(

	Long id,
	String title,
	String content,
	UserSummary user,
	long commentCnt
) {
	public static GetCommunitiesResponse of(Community community, Long commentCnt) {
		return GetCommunitiesResponse.builder()
			.id(community.getId())
			.title(community.getTile())
			.content(community.getContent())
			.user(UserSummary.from(community.getUser()))
			.commentCnt(commentCnt)
			.build();
	}
}
