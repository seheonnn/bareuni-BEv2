package com.bareuni.bareuniv2.batch;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.user.UserImage;
import com.bareuni.coredomain.domain.user.repository.UserImageRepository;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class SchedulerConfig {

	private final S3Service s3Service;
	private final UserImageRepository userImageRepository;

	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteNullUserImage() {
		// TODO 추후 모듈 분리
		List<UserImage> userImages = userImageRepository.findAllByUserIsNull();
		userImages.forEach(userImage -> {
			s3Service.deleteFile(userImage.getUrl());
			userImageRepository.delete(userImage);
		});
	}

}
