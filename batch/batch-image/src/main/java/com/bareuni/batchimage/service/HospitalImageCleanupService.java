package com.bareuni.batchimage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bareuni.coredomain.domain.hospital.HospitalImage;
import com.bareuni.coredomain.domain.hospital.repository.HospitalImageRepository;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalImageCleanupService {

	private final HospitalImageRepository hospitalImageRepository;
	private final S3Service s3Service;

	@Transactional
	public void cleanupUnlinkedImages() {
		List<HospitalImage> hospitalImages = hospitalImageRepository.findAllByHospitalIsNull();
		hospitalImages.forEach(hospitalImage -> {
			s3Service.deleteFile(hospitalImage.getUrl());
			hospitalImageRepository.delete(hospitalImage);
		});
	}
}
