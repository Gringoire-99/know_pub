package com.gg.kp_common.utils.proxy;

import com.gg.kp_common.utils.ValidationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateProxy {
    @Pointcut(value = "execution(public * com.gg.kp_common.service.impl.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        ValidationUtils.validate().validateEmpty(joinPoint.getArgs());
    }
}
