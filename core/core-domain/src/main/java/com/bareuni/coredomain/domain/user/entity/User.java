package com.bareuni.coredomain.domain.user.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.RoleType;
import com.bareuni.coredomain.global.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Column(name = "userIdx")
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "phone")
	private String phone;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private GenderType gender;

	@Column(name = "age", nullable = false)
	private int age;

	// 치아 교정 여부
	@Column(name = "ortho", nullable = false)
	@ColumnDefault("false")
	private boolean ortho;

	@Enumerated(value = EnumType.STRING)
	@ColumnDefault("'USER'")
	@Column(name = "role", nullable = false)
	private RoleType role;
}
