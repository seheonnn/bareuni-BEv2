package com.bareuni.coredomain.domain.review;

import java.util.ArrayList;
import java.util.List;

import com.bareuni.coredomain.domain.user.User;
import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "review")
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 별점
	@Column(nullable = false)
	private int totalScore;

	// 진료 결과 점수
	@Enumerated(value = EnumType.STRING)
	@Column(name = "treatment_score", nullable = false)
	private ReviewType treatmentScore;

	// 서비스 품질 점수
	@Enumerated(value = EnumType.STRING)
	@Column(name = "service_score", nullable = false)
	private ReviewType serviceScore;

	// 시설 및 장비 점수
	@Enumerated(value = EnumType.STRING)
	@Column(name = "equipment_score", nullable = false)
	private ReviewType equipmentScore;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Long payment;

	@Column(nullable = false)
	private boolean receipt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Builder.Default
	@OneToMany(mappedBy = "review", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReviewImage> reviewImages = new ArrayList<>();
}
