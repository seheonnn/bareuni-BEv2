package com.bareuni.bareuniv2.domain.community.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.bareuniv2.domain.community.converter.CommunityImageConverter;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityRequest;
import com.bareuni.bareuniv2.domain.community.dto.CreateCommunityResponse;
import com.bareuni.bareuniv2.domain.community.dto.UploadCommunityImageResponse;
import com.bareuni.bareuniv2.domain.community.exception.CommunityErrorCode;
import com.bareuni.bareuniv2.domain.community.exception.CommunityException;
import com.bareuni.coredomain.domain.community.Community;
import com.bareuni.coredomain.domain.community.CommunityImage;
import com.bareuni.coredomain.domain.community.repository.CommunityImageRepository;
import com.bareuni.coredomain.domain.community.repository.CommunityRepository;
import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coreinfras3.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommunityService {

	private final CommunityRepository communityRepository;
	private final CommunityImageRepository communityImageRepository;
	private final S3Service s3Service;

	public UploadCommunityImageResponse uploadCommunityImage(MultipartFile file, int order) {
		String url = s3Service.uploadImage(file);
		return UploadCommunityImageResponse.from(
			communityImageRepository.save(CommunityImageConverter.toEntity(url, order))
		);
	}

	public CreateCommunityResponse createCommunity(User user, CreateCommunityRequest request) {
		Community community = request.toEntity();
		community.setUser(user);

		List<String> imageUrls = request.imageUrls();
		if (imageUrls != null && !imageUrls.isEmpty()) {
			List<CommunityImage> communityImages = communityImageRepository.findAllByUrlInAndCommunityIsNull(imageUrls);

			List<String> foundUrls = communityImages.stream()
				.map(CommunityImage::getUrl)
				.toList();

			// 업로드되지 않은 이미지 주소로 접근 시 에러
			if (!foundUrls.containsAll(imageUrls)) {
				throw new CommunityException(CommunityErrorCode.COMMUNITY_IMAGE_ERROR);
			}
			communityImages.forEach(community::addCommunityImage);
		}

		communityRepository.save(community); // Community와 CommunityImage들을 함께 저장

		return CreateCommunityResponse.from(community);
	}
}
