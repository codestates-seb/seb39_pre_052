package com.seb39.mystackoverflow.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member를 찾을 수 없습니다."),
    MEMBER_EXISTS(409, "이미 존재하는 Member입니다."),
    QUESTION_NOT_FOUND(404, "Question을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(404, "Comment를 찾을 수 없습니다."),
    ANSWER_NOT_FOUND(404, "Answer를 찾을 수 없습니다."),
    PERMISSION_DENIED(403, "접근이 거부되었습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
