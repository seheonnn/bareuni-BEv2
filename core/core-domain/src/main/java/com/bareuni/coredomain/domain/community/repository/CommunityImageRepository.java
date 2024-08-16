package com.bareuni.coredomain.domain.community.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.community.CommunityImage;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {

	List<CommunityImage> findAllByCommunityIsNull();
}
