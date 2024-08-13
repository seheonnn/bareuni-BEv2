package com.bareuni.coredomain.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.community.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
