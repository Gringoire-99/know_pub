package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.entity.vo.RootCommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    Integer isLiked(String commentId, String userId);

    Integer like(String commentId, String userId);

    Integer unlike(String commentId, String userId);

    IPage<RootCommentVo> commentPage(IPage<Comment> page, String postId);

    List<CommentVo> getCommentChildren(String rootCommentId);

    IPage<CommentVo> getChildrenComment(IPage<Comment> page, String rootCommentId);
}
