package com.bareuni.coredomain.global;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();

	ApiResponse<Void> getErrorResponse();
}
