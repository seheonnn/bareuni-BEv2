package com.bareuni.coredomain.domain.community.repository;

import static com.bareuni.coredomain.domain.community.QCommunity.*;
import static com.bareuni.coredomain.domain.user.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.bareuni.coredomain.domain.community.Community;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class CommunityCustomRepositoryImpl implements CommunityCustomRepository {

	private final JPAQueryFactory queryFactory;

	public CommunityCustomRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Community> getCommunities(Pageable pageable) {
		List<Community> content = queryFactory
			.selectFrom(community)
			.leftJoin(community.user, user).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(community.count())
			.from(community);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}
}
