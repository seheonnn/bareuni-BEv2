package com.bareuni.coredomain.domain.comment.repository;

import static com.bareuni.coredomain.domain.comment.QComment.*;
import static com.bareuni.coredomain.domain.user.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.bareuni.coredomain.domain.comment.Comment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class CommentCustomRepositoryImpl implements CommentCustomRepository {

	private final JPAQueryFactory queryFactory;

	public CommentCustomRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Comment> getComments(Long communityId, Pageable pageable) {
		List<Comment> content = queryFactory
			.selectFrom(comment)
			.leftJoin(comment.user, user).fetchJoin()
			.where(comment.community.id.eq(communityId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(comment.count())
			.from(comment)
			.where(comment.community.id.eq(communityId));

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}
}
