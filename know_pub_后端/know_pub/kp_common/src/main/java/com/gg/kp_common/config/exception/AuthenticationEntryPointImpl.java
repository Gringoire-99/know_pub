package com.gg.kp_common.config.exception;

import com.alibaba.fastjson.JSON;
import com.gg.kp_common.utils.HttpEnum;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        Result<?> result = null;
        if (authException instanceof BadCredentialsException) {
            result = Result.error(HttpEnum.USER_NOT_FOUND.getCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = Result.error(HttpEnum.UNAUTHORIZED);
        } else {
            result = Result.error();
        }
        //响应给前端

        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}