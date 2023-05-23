package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.RegisterUser;
import com.gg.kp_common.entity.vo.UserInfoVo;
import com.gg.kp_common.entity.vo.UserVo;
import com.gg.kp_common.utils.Result;

public interface IUserService extends IService<User> {
    Result<UserInfoVo> login(User user);

    Result<UserVo> infoDetail(String userId);

    Result<?> logout();

    Result<?> register(RegisterUser user);
}
