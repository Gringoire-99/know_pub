package com.gg.kp_common.config.exception;

import com.gg.kp_common.utils.HttpEnum;

public class SystemException extends RuntimeException {
    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(HttpEnum httpEnum) {
        super(httpEnum.getMsg());
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }

}