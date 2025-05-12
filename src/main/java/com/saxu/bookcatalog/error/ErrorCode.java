package com.saxu.bookcatalog.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(100001, "INTERNAL_SERVER_ERROR", ErrorType.APPLICATION, "unknown internal system error"),

    IS_NULL(100002, "IS_NULL", ErrorType.REQUEST, "current entity is null"),

    ILLEGAL_PARAM(100003, "ILLEGAL_PARAM", ErrorType.REQUEST, "parameter is illegal, please check the input argument convention, this error code is mostly used for api parameter validation"),

    AUTHOR_FIELD_TOO_LONG(100004, "AUTHOR_FIELD_TOO_LONG", ErrorType.REQUEST, "The author field is too long"),

    TITLE_FIELD_TOO_LONG(100005, "TITLE_FIELD_TOO_LONG", ErrorType.REQUEST, "The title field is too long"),

    BOOK_TITLE_EXISTS(100006, "BOOK_TITLE_EXISTS", ErrorType.REQUEST,  "The book title already exists");
    ;


    private final long errorId;
    private final String errorCode;
    private final String errorMsg;
    private final ErrorType errorType;

    ErrorCode(long errorId, String code, ErrorType type, String desc) {
        this.errorId = errorId;
        this.errorCode = code;
        this.errorType = type;
        this.errorMsg = desc;
    }

}
