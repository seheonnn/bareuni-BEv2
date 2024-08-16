package com.bareuni.batchimage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.user.UserImage;
import com.bareuni.coredomain.domain.user.repository.UserImageRepository;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserImageCleanupService {

	private final UserImageRepository userImageRepository;
	private final S3Service s3Service;

	@Transactional
	public void cleanupUnlinkedImages() {
		List<UserImage> userImages = userImageRepository.findAllByUserIsNull();
		userImages.forEach(userImage -> {
			s3Service.deleteFile(userImage.getUrl());
			userImageRepository.delete(userImage);
		});
	}
}
