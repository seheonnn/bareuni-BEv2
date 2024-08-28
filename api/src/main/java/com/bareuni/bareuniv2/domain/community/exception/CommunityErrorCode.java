package com.bareuni.bareuniv2.domain.community.exception;

import org.springframework.http.HttpStatus;

import com.bareuni.coredomain.global.ApiResponse;
import com.bareuni.coredomain.global.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityErrorCode implements BaseErrorCode {

	COMMUNITY_ERROR(HttpStatus.BAD_REQUEST, "COMMU4000", "커뮤니티 관련 에러"),
	COMMUNITY_IMAGE_ERROR(HttpStatus.BAD_REQUEST, "COMMU4001", "커뮤니티 이미지 에러"),
	COMMUNITY_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMU4030", "해당 커뮤니티에 대한 권한이 없습니다."),
	COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMU4040", "해당 커뮤니티를 찾을 수 없습니다."),
	COMMUNITY_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMU4041", "해당 커뮤니티의 댓글을 찾을 수 없습니다."),
	;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
