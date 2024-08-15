package com.bareuni.coredomain.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.like.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}
