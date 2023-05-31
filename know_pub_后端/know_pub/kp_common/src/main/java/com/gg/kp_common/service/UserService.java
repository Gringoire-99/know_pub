package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.*;
import com.gg.kp_common.utils.Result;

import java.util.List;

public interface UserService extends IService<User> {
    Result<UserVo> login(User user);

    Result<UserInfoDetailVo> infoDetail(String userId);

    Result<?> logout();

    Result<?> register(RegisterUser user);

    Result<UserInfoShortVo> infoShort(String userId);

    Result<UserPostCardVo> postCard(String userId);

    Result<?> follow(String userId);

    Result<List<UserInfoShortVo>> getRecommendedUser();

    Result<?> updateUserInfo(UpdateUser user);

    Result<Integer> updateAvatar(String avatar);
}
