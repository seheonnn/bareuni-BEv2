package com.bareuni.bareuniv2.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.auth.dto.UserRegisterRequest;
import com.bareuni.bareuniv2.auth.dto.UserRegisterResponse;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.repository.UserRepository;
import com.bareuni.coreinfrasecurity.jwt.dto.JwtDto;
import com.bareuni.coreinfrasecurity.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public UserRegisterResponse register(UserRegisterRequest request) {

		String encodedPw = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPw);

		return UserRegisterResponse.from(userRepository.save(newUser));
	}

	public JwtDto reissueToken(String refreshToken) {
		return jwtUtil.reissueToken(refreshToken);
	}
}
