package com.gg.kp_common.utils;

import com.gg.kp_common.config.exception.SystemException;

import java.util.ArrayList;
import java.util.Objects;

public class ValidationUtils {
//    验证手机号的正则表达式，要求11位数字，且以1开头
    public static final String REGEX_MOBILE = "^1[3|4|5|7|8][0-9]\\d{8}$";
//    验证邮箱的正则表达式，要求以字母开头，字母、数字、下划线、中划线组成，@符号前后都要有内容，@符号后面要有.符号
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
//    验证密码的正则表达式，要求8位以上,30位以下
    public static final String REGEX_PASSWORD = "^.{8,30}$";
//    验证用户名的正则表达式，要求4位以上，16位以下，字母、数字、下划线、中划线组成
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9_-]{4,16}$";
    public static final String EM_NULL = "参数不能为空";
    public static final String EM_MOBILE = "手机号格式不正确";
    public static final String EM_EMAIL = "邮箱格式不正确";
    public static final String EM_PASSWORD = "密码格式不正确";
    public static final String EM_NAME = "用户名格式不正确";
    public static final String EM_USERNAME = "手机号或邮箱格式不正确";

    public static ValidationUtils validate(){
        return new ValidationUtils();
    }
    public ValidationUtils validateNull(Object... objects ) {
        for (Object object : objects) {
            if (Objects.isNull(object)) {
                throw new SystemException(HttpEnum.ERROR, EM_NULL);
            }
        }
        return this;
    }
    public ValidationUtils validateEmpty(Object... objects ) {
        for (Object object : objects) {
            if (Objects.isNull(object)||object.toString().isEmpty()) {
                throw new SystemException(HttpEnum.ERROR, EM_NULL);
            }
        }
        return this;
    }
    public ValidationUtils validateMobile(String mobile) {
        if (!mobile.matches(REGEX_MOBILE)) {
            throw new SystemException(HttpEnum.ERROR, EM_MOBILE);
        }
        return this;
    }
    public ValidationUtils validateEmail(String email) {
        if (!email.matches(REGEX_EMAIL)) {
            throw new SystemException(HttpEnum.ERROR, EM_EMAIL);
        }
        return this;
    }
    public ValidationUtils validatePassword(String password) {
        if (!password.matches(REGEX_PASSWORD)) {
            throw new SystemException(HttpEnum.ERROR, EM_PASSWORD);
        }
        return this;
    }

    /**
     * 验证用户昵称
     */
    public ValidationUtils validateName(String name) {
        if (!name.matches(REGEX_USERNAME)) {
            throw new SystemException(HttpEnum.ERROR, EM_NAME);
        }
        return this;
    }

    /**
     *验证用户的邮箱或者手机号
     */
    public ValidationUtils ValidateUsername(String username) {
        if (!username.matches(REGEX_EMAIL) && !username.matches(REGEX_MOBILE)) {
            throw new SystemException(HttpEnum.ERROR, EM_USERNAME);
        }
        return this;
    }

}
