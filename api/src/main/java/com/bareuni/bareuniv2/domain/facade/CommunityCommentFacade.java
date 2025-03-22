package com.bareuni.bareuniv2.domain.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentRequest;
import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentResponse;
import com.bareuni.bareuniv2.domain.comment.service.CommentService;
import com.bareuni.bareuniv2.domain.community.service.CommunityQueryService;
import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityCommentFacade {

	private final CommunityQueryService communityService;
	private final CommentService commentService;

	public CreateCommentResponse createComment(Long communityId, User user, CreateCommentRequest request) {

		Community community = communityService.getCommunityById(communityId);
		
		Comment comment = request.toEntity();
		community.addComment(comment);
		comment.setUser(user);

		Comment savedComment = commentService.createComment(comment);
		return CreateCommentResponse.from(savedComment);
	}
}
