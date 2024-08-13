package com.bareuni.coredomain.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
