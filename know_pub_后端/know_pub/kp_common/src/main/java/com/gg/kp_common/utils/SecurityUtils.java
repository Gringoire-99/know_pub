package com.gg.kp_common.utils;

import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.entity.po.UserDetail;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    public static String getId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) throw new SystemException(HttpEnum.ERROR,"未授权");
        return (String) authentication.getPrincipal();
    }
}
