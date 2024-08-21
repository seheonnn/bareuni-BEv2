package com.bareuni.coredomain.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bareuni.coredomain.domain.community.Community;

public interface CommunityCustomRepository {

	Page<Community> getCommunities(Pageable pageable);
}
