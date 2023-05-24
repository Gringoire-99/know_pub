package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.kp_common.entity.po.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
