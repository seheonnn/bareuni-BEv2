package com.bareuni.bareuniv2.domain.test;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bareuni.bareuniv2.auth.annotation.UserResolver;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.repository.UserRepository;
import com.bareuni.coredomain.global.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
// @RequestMapping("/api/v2/auth")
@RestController
public class TestController {

	private final UserRepository userRepository;

	@GetMapping("/test-user")
	public ApiResponse<User> testUserProfile(@UserResolver User user) {
		user.setUserImage(null);
		userRepository.save(user);
		return ApiResponse.onSuccess(user);
	}

	@GetMapping("/test-user-resolver")
	public ApiResponse<String> testUserResolver(@UserResolver User user) {
		log.info(user.getUserImage().getUrl());
		return ApiResponse.onSuccess(user.getEmail());
	}
}
