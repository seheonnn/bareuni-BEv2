package com.bareuni.bareuniv2.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.comment.dto.GetCommentsResponse;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {

	private final CommentRepository commentRepository;

	public PageResponse<GetCommentsResponse> getComments(Long id, PageCondition pageCondition) {

		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		Page<Comment> comments = commentRepository.getComments(id, pageable);

		return PageResponse.of(comments.map(GetCommentsResponse::from));
	}
}
