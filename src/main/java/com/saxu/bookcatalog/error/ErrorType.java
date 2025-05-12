package com.saxu.bookcatalog.error;

import lombok.Getter;

@Getter
public enum ErrorType {
    APPLICATION("APPLICATION", 500, "system error"),

    BUSINESS("BUSINESS", 500, "business error"),

    REQUEST("REQUEST", 400, "bad request"),
    ;

    ErrorType(String code, int httpCode, String msg) {
        this.code = code;
        this.msg = msg;
        this.httpCode = httpCode;
    }

    private String code;

    private String msg;

    private int httpCode;

}
