package com.bareuni.bareuniv2.domain.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.domain.community.dto.CreateCommentRequest;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.dto.GetCommunitiesResponse;
import com.bareuni.bareuniv2.domain.community.dto.UpdateCommentRequest;
import com.bareuni.bareuniv2.domain.community.dto.UpdateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.exception.CommunityErrorCode;
import com.bareuni.bareuniv2.domain.community.exception.CommunityException;
import com.bareuni.bareuniv2.domain.community.service.CommunityQueryService;
import com.bareuni.bareuniv2.domain.community.service.CommunityService;
import com.bareuni.bareuniv2.domain.page.PageCondition;
import com.bareuni.bareuniv2.domain.page.PageResponse;
import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.comment.repository.CommentRepository;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.community.repository.CommunityRepository;
import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
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
	@Autowired
	CommentRepository commentRepository;
	private User user;
	private Long communityId;
	private Long commentId;

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

		Community community = Community.builder()
			.tile("커뮤니티 제목")
			.content("커뮤니티 내용")
			.user(user)
			.build();
		em.persist(community);
		communityId = community.getId();

		Comment comment = Comment.builder()
			.community(community)
			.content("댓글 내용")
			.user(user)
			.build();
		em.persist(comment);
		commentId = comment.getId();
	}

	@Test
	@DisplayName("커뮤니티 생성 테스트")
	void createCommunity() {
		// given
		CreateCommunityRequest request = new CreateCommunityRequest("커뮤니티 생성 테스트", "커뮤니티 생성 테스트", null);

		// when
		Long id = communityService.createCommunity(user, request).id();

		//then
		Community savedCommunity = communityRepository.findByIdWithUser(id).orElseThrow();
		Assertions.assertEquals(request.title(), savedCommunity.getTile());
	}

	@Test
	@DisplayName("커뮤니티 조회 테스트")
	void getCommunitiesTest() {

		// given
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

		PageCondition pageCondition = new PageCondition(1, 10); // 페이지 조건 설정

		// when
		PageResponse<GetCommunitiesResponse> communities = communityQueryService.getCommunities(pageCondition);

		// then
		Assertions.assertNotNull(communities, "결과가 null이 아님을 확인합니다.");
		Assertions.assertEquals(10, communities.pageSize(), "페이지당 커뮤니티 개수가 10개인지 확인합니다.");
	}

	@Test
	@DisplayName("커뮤니티 업데이트 테스트")
	void updateCommunityTest() {
		// given
		UpdateCommunityRequest request = new UpdateCommunityRequest("커뮤니티 수정 테스트", null, null);

		// when
		commentId = communityService.updateCommunity(communityId, user, request).id();

		// then
		Community community = communityRepository.findById(commentId).orElseThrow();
		Assertions.assertEquals("커뮤니티 수정 테스트", community.getTile());
	}

	@Test
	@DisplayName("커뮤니티 업데이트 exception 테스트")
	void updateCommunityExceptionTest() {
		// given
		User newUser = User.builder()
			.email("test2@example.com")
			.username("testUser2")
			.password("password")
			.gender(GenderType.MALE)
			.age(30)
			.role(RoleType.USER)
			.build();
		UpdateCommunityRequest request = new UpdateCommunityRequest("커뮤니티 수정 테스트", null, null);

		// when
		CommunityException exception = Assertions.assertThrows(CommunityException.class, () -> {
			communityService.updateCommunity(communityId, newUser, request);
		});

		// then
		Assertions.assertEquals(CommunityErrorCode.COMMUNITY_FORBIDDEN, exception.getErrorCode());
	}

	@Test
	@DisplayName("댓글 생성 테스트")
	void createCommentTest() {
		// given
		CreateCommentRequest createCommentRequest = new CreateCommentRequest("댓글 생성 테스트");

		// when
		commentId = communityService.createComment(communityId, user, createCommentRequest).id();

		//then
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		Assertions.assertEquals("댓글 생성 테스트", comment.getContent());
	}

	@Test
	@DisplayName("댓글 수정 테스트")
	void updateCommentTest() {
		// given
		UpdateCommentRequest request = new UpdateCommentRequest("댓글 수정 테스트");

		// when
		commentId = communityService.updateComment(communityId, user, request, commentId).id();

		//then
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		Assertions.assertEquals("댓글 수정 테스트", comment.getContent());
	}
}
