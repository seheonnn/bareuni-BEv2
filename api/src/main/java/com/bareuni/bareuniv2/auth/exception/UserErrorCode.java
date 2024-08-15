package com.bareuni.bareuniv2.auth.exception;

import org.springframework.http.HttpStatus;

import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coredomain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

	USER_ERROR(HttpStatus.BAD_REQUEST, "USR4000", "사용자 관련 에러"),
	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
