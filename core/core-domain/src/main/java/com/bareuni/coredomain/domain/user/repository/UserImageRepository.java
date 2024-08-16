package com.bareuni.coredomain.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.user.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

	Optional<UserImage> findUserImageByUrl(String url);

	List<UserImage> findAllByUserIsNull();
}
