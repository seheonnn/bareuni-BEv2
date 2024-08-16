package com.bareuni.bareuniv2.domain.user.dto;

import com.bareuni.coredomain.domain.user.GenderType;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UpdateUserRequest(
	String username,
	@Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "[ERROR] 전화번호 형식에 맞지 않습니다.")
	String phone,
	GenderType gender,
	Integer age,
	Boolean ortho,
	String profileUrl
) {
}
