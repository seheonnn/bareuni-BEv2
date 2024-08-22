package com.bareuni.coredomain.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

	Optional<User> findByEmail(String username);

	// @Query("SELECT u FROM User u LEFT JOIN FETCH u.userImage WHERE u.id = :id")
	// Optional<User> findByIdWithUserImage(Long id);
}
