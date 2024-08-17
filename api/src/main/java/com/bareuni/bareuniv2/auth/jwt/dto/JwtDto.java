package com.bareuni.bareuniv2.auth.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
