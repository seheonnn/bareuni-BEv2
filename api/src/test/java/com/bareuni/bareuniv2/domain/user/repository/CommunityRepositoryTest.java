package com.bareuni.bareuniv2.domain.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.community.repository.CommunityRepository;
import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@DataJpaTest // JPA 관련 컨텍스트만 로드
// @SpringBootTest // 통합 테스트, 애플리케이션의 모든 빈 로드
@Transactional
class CommunityRepositoryTest {

	@Autowired
	EntityManager em;
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
	}

	@Test
	void save() {
		// given
		Community community = Community.builder()
			.tile("커뮤니티 제목")
			.content("커뮤니티 내용")
			.user(user)
			.build();

		CommunityImage communityImage = CommunityImage.builder()
			.url("https://example.webp")
			.build();

		community.addCommunityImage(communityImage);

		// when
		communityRepository.save(community);

		//then

	}
}
