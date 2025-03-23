package com.bareuni.coredomain.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bareuni.coredomain.domain.comment.Comment;

public interface CommentCustomRepository {
	Page<Comment> getComments(Long communityId, Pageable pageable);
}
