package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.kp_common.entity.po.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    Integer isLiked(String commentId, String userId);
    Integer like(String commentId, String userId);
    Integer unlike(String commentId, String userId);
}
