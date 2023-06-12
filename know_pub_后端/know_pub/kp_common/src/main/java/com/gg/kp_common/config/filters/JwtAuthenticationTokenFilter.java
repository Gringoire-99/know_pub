package com.gg.kp_common.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.utils.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求头中的token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录 直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时 token非法
            //响应告诉前端需要重新登录
            Result<?> result = Result.error(HttpEnum.UNAUTHORIZED);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取用户信息
        UserDetail userDetail = redisCache.getCacheObject("userId:" + userId);
        //如果获取不到
        if (Objects.isNull(userDetail)) {
            //说明登录过期 提示重新登录
            Result<?> result =
                    Result.error(HttpEnum.UNAUTHORIZED);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetail.getUser().getId(), userDetail.getPassword(), userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
