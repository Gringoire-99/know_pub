package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.entity.vo.PostComment;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

import java.util.HashMap;
import java.util.Map;

public interface CommentService extends IService<Comment> {
    Result<HashMap<String, Object>> getPostComment(PageParams params, String postId);

    Result<Integer> postComment(PostComment comment);

    Result<HashMap<String, Object>> getChildComment(PageParams params, String commentId);

    Result<Integer> likeComment(Map<String, Object> params);
}
