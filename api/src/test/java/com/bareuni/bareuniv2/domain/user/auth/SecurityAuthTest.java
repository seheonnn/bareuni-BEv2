package com.bareuni.bareuniv2.domain.user.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.auth.dto.JoinUserRequest;
import com.bareuni.bareuniv2.auth.service.AuthService;
import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SecurityAuthTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthService authService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입 테스트")
	void joinTest() {

		// given
		JoinUserRequest request = new JoinUserRequest("bareuni@gmail.com",
			"bareuni1234#",
			"이바른",
			"010-1234-1234",
			GenderType.MALE,
			25,
			true,
			null);

		// when
		Long newUserId = authService.join(request).id();

		// then
		Long savedUserId = userRepository.findById(1L).get().getId();
		Assertions.assertThat(newUserId.equals(savedUserId));
	}

	@Test
	@DisplayName("로그인 테스트")
	void loginTest() throws Exception {

		// given
		User user = User.builder()
			.email("bareuni@gmail.com")
			.password(passwordEncoder.encode("bareuni1234#"))
			.username("이바른")
			.phone("010-1234-1234")
			.gender(GenderType.MALE)
			.age(25)
			.ortho(true)
			.role(RoleType.USER)
			.userImage(null)
			.build();
		userRepository.save(user);

		// when
		String jsonRequest = "{"
			+ "\"email\": \"bareuni@gmail.com\", "
			+ "\"password\": \"bareuni1234#\" "
			+ "}";

		MvcResult response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v2/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonRequest)) // JSON 형식의 데이터를 본문에 추가
			.andReturn();

		// then
		Assertions.assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
}
