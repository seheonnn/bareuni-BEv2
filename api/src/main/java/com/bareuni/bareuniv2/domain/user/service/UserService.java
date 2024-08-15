package com.bareuni.bareuniv2.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.domain.user.converter.UserConverter;
import com.bareuni.bareuniv2.domain.user.dto.UploadProfileImageResponse;
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
}
