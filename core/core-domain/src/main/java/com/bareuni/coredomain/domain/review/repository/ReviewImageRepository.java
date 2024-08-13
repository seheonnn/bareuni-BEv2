package com.bareuni.coredomain.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.review.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
