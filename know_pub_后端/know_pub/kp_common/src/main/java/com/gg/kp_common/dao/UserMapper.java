package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.kp_common.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Integer addUserRole(String userId);
}
