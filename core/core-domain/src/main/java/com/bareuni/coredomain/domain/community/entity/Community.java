package com.bareuni.coredomain.domain.community.entity;

import java.util.ArrayList;
import java.util.List;

import com.bareuni.coredomain.domain.comment.entity.Comment;
import com.bareuni.coredomain.domain.user.entity.User;
import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "community")
public class Community extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<CommunityImage> communityImages = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "community", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
}
