package com.bareuni.bareuniv2.global.exception;

import com.bareuni.coredomain.global.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
