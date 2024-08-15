package com.bareuni.coredomain.domain.hospital;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bareuni.coredomain.domain.review.Review;
import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "hospital")
public class Hospital extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String hospitalName;

	@Column(nullable = false)
	// @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞지 않습니다.")
	private String telephone;

	@Column(nullable = false)
	private String keywords;

	@Column(nullable = false)
	private LocalDateTime openTime;

	@Column(nullable = false)
	private LocalDateTime closedDay;

	@Column(nullable = false)
	private LocalDateTime lunchTime;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private boolean bookable;

	@Column(nullable = false)
	private String summary;

	@Builder.Default
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private List<Review> reviews = new ArrayList<>();

	@Builder.Default
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private List<HospitalImage> hospitalImages = new ArrayList<>();

}
