package com.bareuni.bareuniv2.domain.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.community.dto.GetCommunityResponse;
import com.bareuni.bareuniv2.domain.community.service.CommunityQueryService;
import com.bareuni.bareuniv2.domain.community.service.CommunityService;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.community.repository.CommunityRepository;
import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class CommunityServiceTest {

	@Autowired
	EntityManager em;
	@Autowired
	CommunityService communityService;
	@Autowired
	CommunityQueryService communityQueryService;
	@Autowired
	CommunityRepository communityRepository;

	private User user;

	@BeforeEach
	void setUp() {

		user = User.builder()
			.email("test@example.com")
			.username("testUser")
			.password("password")
			.gender(GenderType.MALE)
			.age(30)
			.role(RoleType.USER)
			.build();

		UserImage userImage = UserImage.builder()
			.user(user)
			.url("http://example.com/image.jpg")
			.build();

		user.setUserImage(userImage);

		em.persist(user);

		for (int i = 0; i < 50; i++) {

			Community community = Community.builder()
				.tile("커뮤니티 제목 " + i)
				.content("커뮤니티 내용 " + i)
				.user(user)
				.build();

			for (int j = 0; j < 5; j++) {
				CommunityImage communityImage = CommunityImage.builder()
					.url("https://example.webp " + i)
					.build();

				community.addCommunityImage(communityImage);
			}
			em.persist(community);
		}
	}

	@Test
	@DisplayName("커뮤니티 조회 테스트")
	void getCommunitiesTest() {

		// given
		PageCondition pageCondition = new PageCondition(1, 10); // 페이지 조건 설정

		// when
		PageResponse<GetCommunityResponse> communities = communityQueryService.getCommunities(pageCondition);

		// then
		Assertions.assertNotNull(communities, "결과가 null이 아님을 확인합니다.");
		Assertions.assertEquals(10, communities.pageSize(), "페이지당 커뮤니티 개수가 10개인지 확인합니다.");
	}

	@Test
	@DisplayName("커뮤니티 업데이트 테스트")
	public void updateCommunityTest() {
		// given
		Community community = Community.builder()
			.tile("커뮤니티 제목")
			.content("커뮤니티 내용")
			.user(user)
			.build();

		// when
		community.update("커뮤니티 수정 테스트", null);

		//then
		Assertions.assertEquals(community.getTile(), "커뮤니티 수정 테스트");
	}
}
