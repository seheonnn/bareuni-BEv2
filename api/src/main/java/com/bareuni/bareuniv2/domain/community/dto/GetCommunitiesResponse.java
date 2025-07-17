package com.bareuni.bareuniv2.domain.community.dto;

import java.util.List;

import com.bareuni.bareuniv2.domain.comment.dto.GetCommentsResponse;
import com.bareuni.bareuniv2.domain.user.dto.UserSummary;
import com.bareuni.coredomain.domain.community.Community;

import lombok.Builder;

@Builder
public record GetCommunitiesResponse(

	Long id,
	String title,
	String content,
	UserSummary user,
	List<GetCommentsResponse> comments,
	int commentCnt
) {
	public static GetCommunitiesResponse from(Community community) {

		List<GetCommentsResponse> comments = community.getComments().stream()
			.map(GetCommentsResponse::from)
			.toList();

		return GetCommunitiesResponse.builder()
			.id(community.getId())
			.title(community.getTitle())
			.content(community.getContent())
			.user(UserSummary.from(community.getUser()))
			.comments(comments)
			.commentCnt(community.getCommentCount())
			.build();
	}
}
