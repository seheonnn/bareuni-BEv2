package com.bareuni.bareuniv2.domain.test;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bareuni.coreinfras3.S3Service;
import com.bareuni.coreinfras3.dto.S3Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v2/s3")
public class S3TestController {

	private final S3Service s3Service;

	@PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
	public String uploadFile(@RequestPart(value = "file", required = false) MultipartFile file) {
		return s3Service.uploadFile(file);
	}

	@PostMapping(value = "/uploadImage", consumes = "multipart/form-data")
	public String uploadImage(@RequestPart(value = "file", required = false) MultipartFile file) {
		return s3Service.uploadImage(file);
	}

	@DeleteMapping(value = "/deleteImage")
	public String deleteImage(@RequestBody String path) {
		String image = path.substring(path.lastIndexOf('/') + 1);
		return s3Service.deleteFile(image);
	}

	@PostMapping(value = "/uploadImages", consumes = "multipart/form-data")
	public List<String> uploadImages(
		@RequestPart(value = "files", required = false) List<MultipartFile> files) {
		return s3Service.uploadFiles(files);
	}

	@GetMapping(value = "/getPreSigned")
	public S3Response getPreSignedUrl(@RequestParam String fileName) {
		return s3Service.getPreSignedUrl(fileName);
	}
}
