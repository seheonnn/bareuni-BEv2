package com.bareuni.coredomain.domain.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bareuni.coredomain.domain.comment.Comment;
import com.bareuni.coredomain.domain.community.Community;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c JOIN FETCH c.user u JOIN FETCH c.community com WHERE c.id = :id")
	Optional<Comment> findByIdWithUserAndCommunity(Long id);

	@Query("SELECT c FROM Comment c JOIN FETCH c.user u JOIN FETCH c.community com WHERE c.community = :community")
	List<Comment> findByCommunityWithUserAndCommunity(Community community);

	Long countByCommunity(Community community);
}
