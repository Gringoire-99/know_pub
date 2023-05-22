package com.gg.kp_common.config.exception;

import com.gg.kp_common.utils.HttpEnum;
import com.gg.kp_common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public Result<?> systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常:", e);
        //从异常对象中获取提示信息封装返回
        return Result.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常:", e);
        //从异常对象中获取提示信息封装返回
        return Result.error(HttpEnum.ERROR.getCode(), e.getMessage());
    }
}
