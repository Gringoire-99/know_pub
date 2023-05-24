package com.gg.kp_common.utils;

import com.gg.kp_common.entity.po.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    public static String getId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication))return null;
        return (String) authentication.getPrincipal();
    }
}
