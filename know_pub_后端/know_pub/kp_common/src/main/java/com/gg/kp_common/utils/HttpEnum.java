package com.gg.kp_common.utils;

import lombok.Data;

public enum HttpEnum {
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    USER_NOT_FOUND(512, "user not found"),
    NOT_FOUND(404, "not found"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden");

    private final Integer code;
    private final String msg;

    HttpEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static HttpEnum getHttpEnum(Integer code) {
        for (HttpEnum httpEnum : HttpEnum.values()) {
            if (httpEnum.getCode().equals(code)) {
                return httpEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
