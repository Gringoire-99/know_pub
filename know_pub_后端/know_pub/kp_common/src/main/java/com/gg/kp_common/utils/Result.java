package com.gg.kp_common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements java.io.Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Result() {
        this.code = HttpEnum.SUCCESS.getCode();
        this.msg = HttpEnum.SUCCESS.getMsg();
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public void setStatus(HttpEnum httpEnum) {
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }
    public void setStatus(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static <T> Result<T> ok(T data) {
        return new Result<T>(HttpEnum.SUCCESS.getCode(), HttpEnum.SUCCESS.getMsg(), data);
    }

    public static Result<?> ok(String msg) {
        return new Result<>(HttpEnum.SUCCESS.getCode(), msg);
    }
    public static Result<?> ok() {
        return new Result<>(HttpEnum.SUCCESS.getCode(), HttpEnum.SUCCESS.getMsg());
    }

    public static Result<?> error() {
        return new Result<>(HttpEnum.ERROR.getCode(), HttpEnum.ERROR.getMsg());
    }
    public static Result<?> error(Integer code ,String msg) {
        return new Result<>(code, msg);
    }

}
