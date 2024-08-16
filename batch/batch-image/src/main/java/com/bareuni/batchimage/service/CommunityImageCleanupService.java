package com.bareuni.batchimage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.community.repository.CommunityImageRepository;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommunityImageCleanupService {

	private final CommunityImageRepository communityImageRepository;
	private final S3Service s3Service;

	@Transactional
	public void cleanupUnlinkedImages() {
		List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityIsNull();
		communityImages.forEach(communityImage -> {
			s3Service.deleteFile(communityImage.getUrl());
			communityImageRepository.delete(communityImage);
		});
	}
}
