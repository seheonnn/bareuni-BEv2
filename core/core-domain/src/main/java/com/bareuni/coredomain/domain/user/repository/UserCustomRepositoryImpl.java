package com.bareuni.coredomain.domain.user.repository;

import static com.bareuni.coredomain.domain.user.QUser.*;
import static com.bareuni.coredomain.domain.user.QUserImage.*;

import java.util.Optional;

import com.bareuni.coredomain.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class UserCustomRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory queryFactory;

	public UserCustomRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<User> findByIdWithUserImage(Long id) {
		User result = queryFactory
			.selectFrom(user)
			.leftJoin(user.userImage, userImage).fetchJoin()
			.where(user.id.eq(id))
			.fetchOne();

		return Optional.ofNullable(result);
	}
}
