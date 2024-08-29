package com.bareuni.bareuniv2.domain.community.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.community.dto.GetCommunityResponse;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.comment.repository.CommentRepository;
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
	private final CommentRepository commentRepository;

	public PageResponse<GetCommunityResponse> getCommunities(PageCondition pageCondition) {

		Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
		Page<Community> communities = communityRepository.getCommunities(pageable);

		return PageResponse.of(communities.map(
			community -> {
				Long commentCnt = commentRepository.countByCommunity(community);
				return GetCommunityResponse.of(community, commentCnt);
			}
		));

	}
}
