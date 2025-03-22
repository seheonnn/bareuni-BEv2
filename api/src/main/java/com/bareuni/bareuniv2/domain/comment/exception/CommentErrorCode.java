package com.bareuni.bareuniv2.domain.comment.exception;

import org.springframework.http.HttpStatus;

import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coredomain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {

	COMMENT_ERROR(HttpStatus.BAD_REQUEST, "COMME4000", "댓글 관련 에러"),
	COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMU4030", "해당 댓글에 대한 권한이 없습니다."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMU4040", "해당 댓글을 찾을 수 없습니다."),
	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
