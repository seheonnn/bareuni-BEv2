package com.bareuni.bareuniv2.domain.queue.dto;

public record AllowUserResponse(
	Long requestCount,
	Long allowedCount
) {
}
