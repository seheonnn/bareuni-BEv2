package com.bareuni.batchimage.facade;

import org.springframework.stereotype.Service;

import com.bareuni.batchimage.service.CommunityImageCleanupService;
import com.bareuni.batchimage.service.HospitalImageCleanupService;
import com.bareuni.batchimage.service.UserImageCleanupService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageCleanupFacade {

	private final UserImageCleanupService userImageCleanupService;
	private final CommunityImageCleanupService communityImageCleanupService;
	private final HospitalImageCleanupService hospitalImageCleanupService;

	public void cleanupAllImages() {
		userImageCleanupService.cleanupUnlinkedImages();
		communityImageCleanupService.cleanupUnlinkedImages();
		hospitalImageCleanupService.cleanupUnlinkedImages();
	}
}
