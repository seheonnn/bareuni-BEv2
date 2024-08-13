package com.bareuni.coredomain.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
