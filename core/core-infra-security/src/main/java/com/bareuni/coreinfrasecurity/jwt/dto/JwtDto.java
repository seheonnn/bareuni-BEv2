package com.bareuni.coreinfrasecurity.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
