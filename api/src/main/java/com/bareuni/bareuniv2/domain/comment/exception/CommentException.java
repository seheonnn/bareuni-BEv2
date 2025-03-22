package com.bareuni.bareuniv2.domain.comment.exception;

import com.bareuni.bareuniv2.global.exception.CustomException;
import com.bareuni.coredomain.global.BaseErrorCode;

public class CommentException extends CustomException {
	public CommentException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
