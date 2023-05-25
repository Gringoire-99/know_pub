package com.gg.kp_common.utils;

import com.gg.kp_common.config.exception.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    public static String getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getPrincipal().toString();
        if ("anonymousUser".equals(principal))
            throw new SystemException(HttpEnum.ERROR, "未授权");
        return principal;
    }
}
