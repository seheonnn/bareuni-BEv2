package com.bareuni.bareuniv2.domain.user.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;
import com.bareuni.coredomain.domain.user.repository.UserImageRepository;
import com.bareuni.coredomain.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserImageRepository userImageRepository;

	private Long savedUserId;

	@BeforeEach
	void setUp() {

		User user = userRepository.save(
			User.builder()
				.email("test@example.com")
				.username("testuser")
				.password("password")
				.gender(GenderType.MALE)
				.age(30)
				.role(RoleType.USER)
				.build()
		);

		UserImage userImage = userImageRepository.save(
			UserImage.builder()
				.user(user)
				.url("http://example.com/image.jpg")
				.build()
		);

		// User와 UserImage 간의 연관 관계 설정
		user.setUserImage(userImage);
		savedUserId = userRepository.save(user).getId();
	}

	@Test
	void testUserImageOrphanRemoval() {

		// given
		User findUser = userRepository.findById(savedUserId).orElseThrow();

		// when
		findUser.setUserImage(null);
		userRepository.save(findUser);

		// then
		Optional<UserImage> userImage = userImageRepository.findById(1L);
		Assertions.assertTrue(userImage.isEmpty());
	}
}
