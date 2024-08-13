package com.bareuni.coredomain.domain.user;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "user")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	@Column(nullable = false)
	private String username;

	private String phone;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private GenderType gender;

	@Column(nullable = false)
	private int age;

	// 치아 교정 여부
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean ortho;

	@Enumerated(value = EnumType.STRING)
	@ColumnDefault("'USER'")
	@Column(nullable = false)
	private RoleType role;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private UserImage userImage;
}
