package com.bareuni.bareuniv2.domain.community.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.community.dto.GetCommunitiesResponse;
import com.bareuni.bareuniv2.domain.community.dto.GetCommunityResponse;
import com.bareuni.bareuniv2.domain.community.exception.CommunityErrorCode;
import com.bareuni.bareuniv2.domain.community.exception.CommunityException;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommunityQueryService {

	private final CommunityRepository communityRepository;

	public PageResponse<GetCommunitiesResponse> getCommunities(PageCondition pageCondition) {

		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		Page<Community> communities = communityRepository.getCommunities(pageable);

		return PageResponse.of(communities.map(GetCommunitiesResponse::from));

	}

	public GetCommunityResponse getCommunity(Long id) {

		Community community = communityRepository.findById(id)
			.orElseThrow(() -> new CommunityException(CommunityErrorCode.COMMUNITY_NOT_FOUND));

		return GetCommunityResponse.of(community, community.getCommentCount());

	}

	public Community getCommunityById(Long id) {
		return communityRepository.findByIdWithUser(id)
			.orElseThrow(() -> new CommunityException(CommunityErrorCode.COMMUNITY_NOT_FOUND));
	}

}
