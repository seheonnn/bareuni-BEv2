package com.bareuni.bareuniv2.domain.community.exception;

import com.bareuni.bareuniv2.global.exception.CustomException;
import com.bareuni.coredomain.global.BaseErrorCode;

public class CommunityException extends CustomException {
	public CommunityException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
