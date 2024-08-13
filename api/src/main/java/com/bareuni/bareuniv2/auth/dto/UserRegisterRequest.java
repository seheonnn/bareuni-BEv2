package com.bareuni.bareuniv2.auth.dto;

import com.bareuni.coredomain.domain.user.GenderType;
import com.bareuni.coredomain.domain.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

	@NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String email,

	@NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 8, message = "[ERROR] 비밀번호는 최소 8자리 이이어야 합니다.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,64}$", message = "[ERROR] 비밀번호는 8자 이상, 64자 이하이며 특수문자 한 개를 포함해야 합니다.")
	String password,

	@NotBlank(message = "[ERROR] 사용자명 입력은 필수 입니다.")
	String username,

	@NotBlank(message = "[ERROR] 전화번호 입력은 필수 입니다.")
	@Pattern(regexp = "^01[016789]-?(\\d{3,4})-?(\\d{4})$", message = "[ERROR] 전화번호 형식에 맞지 않습니다.")
	String phone,

	@NotNull(message = "[ERROR] 성별 입력은 필수 입니다.")
	GenderType gender,

	@NotNull(message = "[ERROR] 나이 입력은 필수 입니다.")
	Integer age
) {

	public User toEntity(String encodedPw) {
		return User.builder()
			.email(email)
			.password(encodedPw)
			.username(username)
			.phone(phone)
			.gender(gender)
			.age(age)
			.build();
	}
}
