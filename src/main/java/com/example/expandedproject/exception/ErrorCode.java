package com.example.expandedproject.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    /**
     *  공통 에러 코드
     */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 잘못되었습니다.."),


    /**
     * Member 관련 에러 코드
     */
    DUPLICATED_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    MEMBER_EMPTY(HttpStatus.CONFLICT, "존재하지 않는 사용자입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 인증을 하지 않았습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "사용자 인증 실패"),


    /**
     * Product 관련 에러 코드
     */
    PRODUCT_EMPTY(HttpStatus.CONFLICT, "존재하지 않는 상품입니다."),
    IMAGE_EMPTY(HttpStatus.UNAUTHORIZED, "사진을 첨부해주세요."),
    DUPLICATED_PNAME(HttpStatus.CONFLICT, "중복된 상품 이름이 존재합니다.")


    /**
     * Cart 관련 에러 코드
     */

    ;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private final HttpStatus status;
    private final String message;
}
