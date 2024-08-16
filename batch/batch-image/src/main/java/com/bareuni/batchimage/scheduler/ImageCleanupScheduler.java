package com.bareuni.batchimage.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;

import com.bareuni.batchimage.facade.ImageCleanupFacade;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConfigurationProperties
public class ImageCleanupScheduler {

	private final ImageCleanupFacade imageCleanupFacade;

	@Scheduled(cron = "0 0 0 * * *") // 매일 자정(정각 0시)에 실행
	public void cleanupImages() {
		imageCleanupFacade.cleanupAllImages();
	}
}
