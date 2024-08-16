package com.bareuni.bareuniv2.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.domain.user.converter.UserConverter;
import com.bareuni.bareuniv2.domain.user.dto.UpdateUserRequest;
import com.bareuni.bareuniv2.domain.user.dto.UpdateUserResponse;
import com.bareuni.bareuniv2.domain.user.dto.UploadProfileImageResponse;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.domain.user.repository.UserImageRepository;
import com.bareuni.coredomain.domain.user.repository.UserRepository;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserImageRepository userImageRepository;
	private final S3Service s3Service;

	public UploadProfileImageResponse uploadProfileImage(MultipartFile file) {
		String url = s3Service.uploadImage(file);
		return UploadProfileImageResponse.from(
			userImageRepository.save(UserConverter.toUserImage(url))
		);
	}

	public UpdateUserResponse updateUser(User user, UpdateUserRequest request) {

		user.update(
			request.username(),
			request.phone(),
			request.gender(),
			request.age(),
			request.ortho()
		);

		if (request.profileUrl() != null) {
			// 트랜잭션 문제 발생
			Optional.ofNullable(user.getUserImage()).ifPresent(image -> s3Service.deleteFile(image.getUrl()));
			user.setUserImage(userImageRepository.save(UserConverter.toUserImage(request.profileUrl())));

			// UserImage userImage = userImageRepository.findUserImageByUser(user).orElseGet(() -> {
			// 		UserImage newUserImage = UserConverter.toUserImage(request.profileUrl());
			// 		return userImageRepository.save(newUserImage);
			// 	}
			// );
			// userImage.setUrl(request.profileUrl());
			// user.setUserImage(userImage);
		}
		return UpdateUserResponse.from(userRepository.save(user));
	}
}
