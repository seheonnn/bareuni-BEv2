package com.bareuni.bareuniv2.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.comment.dto.CreateCommentResponse;
import com.bareuni.bareuniv2.domain.comment.dto.UpdateCommentRequest;
import com.bareuni.bareuniv2.domain.comment.dto.UpdateCommentResponse;
import com.bareuni.bareuniv2.domain.comment.exception.CommentErrorCode;
import com.bareuni.bareuniv2.domain.comment.exception.CommentException;
import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.comment.repository.CommentRepository;
import com.bareuni.coredomain.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public Comment createComment(Comment comment) {
		return commentRepository.save(comment);
	}

	public CreateCommentResponse updateComment(Long id, User user, UpdateCommentRequest request, Long commentId) {
		Comment comment = commentRepository.findByIdWithUserAndCommunity(commentId)
			.orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

		if (!comment.getUser().getId().equals(user.getId()))
			throw new CommentException(CommentErrorCode.COMMENT_FORBIDDEN);

		if (!comment.getCommunity().getId().equals(id))
			throw new CommentException(CommentErrorCode.COMMENT_FORBIDDEN);

		comment.update(request.content());
		return UpdateCommentResponse.from(comment);
	}

}
