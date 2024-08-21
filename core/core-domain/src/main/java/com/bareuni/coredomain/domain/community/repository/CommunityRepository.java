package com.bareuni.coredomain.domain.community.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bareuni.coredomain.domain.community.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {

	@Query("SELECT c FROM Community c JOIN FETCH c.user WHERE c.id = :id")
	Optional<Community> findByIdWithUser(Long id);

}
