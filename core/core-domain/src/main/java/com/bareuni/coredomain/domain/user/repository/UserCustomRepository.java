package com.bareuni.coredomain.domain.user.repository;

import java.util.Optional;

import com.bareuni.coredomain.domain.user.User;

public interface UserCustomRepository {

	Optional<User> findByIdWithUserImage(Long id);
}
