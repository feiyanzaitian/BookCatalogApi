package com.saxu.bookcatalog.error;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        this(errorCode, errorCode.getErrorMsg());
    }

    public CommonException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode, Throwable cause, String msg) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

}
