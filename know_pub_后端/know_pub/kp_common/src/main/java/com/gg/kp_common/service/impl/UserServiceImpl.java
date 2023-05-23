package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.entity.vo.RegisterUser;
import com.gg.kp_common.entity.vo.UserInfoVo;
import com.gg.kp_common.entity.vo.UserVo;
import com.gg.kp_common.service.IUserService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<UserInfoVo> login(User user) {
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
        redisCache.setCacheObject("userId:" + id, userDetail);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDetail.getUser(), userVo);
        UserInfoVo userInfoVo = new UserInfoVo(jwt, userVo);
        return Result.ok(userInfoVo);
    }

    @Override
    public Result<UserVo> infoDetail(String userId) {
        User user = this.baseMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new SystemException(HttpEnum.ERROR, "用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return Result.ok(userVo);
    }

    @Override
    public Result<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String userid = userDetail.getUser().getId();
        redisCache.deleteObject("userId:" + userid);
        return Result.ok("退出成功");
    }

    @Override
    public Result<?> register(RegisterUser user) {
        String username = user.getUsername();
        String password = user.getPassword();


        ValidationUtils.validate()
                .validateEmpty(username, password)
                .ValidateUsername(username)
                .validatePassword(password);


        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone, username).or().eq(User::getEmail, username);

        if (!Objects.isNull(this.baseMapper.selectOne(lqw))) {
            throw new SystemException("该用户已注册");
        }


        User u = new User(username, password);
        if (username.contains("@")) {
            u.setEmail(username);
        } else {
            u.setPhone(username);
        }
        String id = JwtUtil.getUUID();
        u.setId(id);
        u.setName("用户_" + id.substring(0, 10));
        u.setPassword(new BCryptPasswordEncoder().encode(password));
        this.baseMapper.insert(u);
        this.baseMapper.addUserRole(u.getId());

        return Result.ok("注册成功");
    }

}
