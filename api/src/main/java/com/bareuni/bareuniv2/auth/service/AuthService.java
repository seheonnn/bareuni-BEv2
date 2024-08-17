package com.bareuni.bareuniv2.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.bareuniv2.auth.dto.JoinUserRequest;
import com.bareuni.bareuniv2.auth.dto.JoinUserResponse;
import com.bareuni.bareuniv2.auth.exception.UserErrorCode;
import com.bareuni.bareuniv2.auth.exception.UserException;
import com.bareuni.bareuniv2.auth.jwt.dto.JwtDto;
import com.bareuni.bareuniv2.auth.jwt.util.JwtUtil;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.UserImage;
import com.bareuni.coredomain.domain.user.repository.UserImageRepository;
import com.bareuni.coredomain.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final UserImageRepository userImageRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public JoinUserResponse join(JoinUserRequest request) {

		String encodedPw = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPw);

		if (request.profileUrl() != null) {
			final UserImage userImage = userImageRepository.findUserImageByUrl(request.profileUrl())
				.orElseThrow(() -> new UserException(UserErrorCode.USER_ERROR));

			newUser.setUserImage(userImage);
		}
		return JoinUserResponse.from(userRepository.save(newUser));
	}

	public JwtDto reissueToken(String refreshToken) {
		return jwtUtil.reissueToken(refreshToken);
	}
}
