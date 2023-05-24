package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.dao.sys.MenuMapper;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.utils.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, username).or().eq(User::getPhone,username);
        User user = baseMapper.selectOne(lqw);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<String> permissionKeyList =  menuMapper.selectPermsByUserId(user.getId());
        return new UserDetail(user,permissionKeyList);
    }
}
