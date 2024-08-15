package com.bareuni.bareuniv2.auth.exception;

import com.bareuni.bareuniv2.global.exception.CustomException;
import com.bareuni.coredomain.global.BaseErrorCode;

public class UserException extends CustomException {
	public UserException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
