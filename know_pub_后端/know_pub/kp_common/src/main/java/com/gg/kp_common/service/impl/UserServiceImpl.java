package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.UserInfoVo;
import com.gg.kp_common.entity.vo.UserVo;
import com.gg.kp_common.service.IUserService;
import com.gg.kp_common.utils.JwtUtil;
import com.gg.kp_common.utils.RedisCache;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<?> login(User user) {
//      需要被验证的对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//      使用manager验证对象
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
//      获取查询到的具体对象和生成的token
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

//      将token保存到redis,并生成视图对象返回
        String id = userDetail.getUser().getId();
        String jwt = JwtUtil.createJWT(id);
        redisCache.setCacheObject("userId:"+id, userDetail);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDetail.getUser(), userVo);
        UserInfoVo userInfoVo = new UserInfoVo(jwt, userVo);
        return Result.ok(userInfoVo);
    }

    @Override
    public Result<UserVo> infoDetail(String userId) {
        User user = this.baseMapper.selectById(userId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return Result.ok(userVo);
    }
    @Override
    public Result<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String userid = userDetail.getUser().getId();
        redisCache.deleteObject("userId:"+userid);
        return Result.ok("退出成功");
    }

}
