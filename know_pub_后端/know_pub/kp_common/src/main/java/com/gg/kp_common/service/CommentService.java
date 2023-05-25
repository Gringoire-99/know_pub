package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.utils.Result;

import java.util.HashMap;
import java.util.Map;

public interface CommentService extends IService<Comment> {
    Result<HashMap<String, Object>> getPostComment(Map<String, Object> params);

    Result<Integer> postComment(CommentVo comment);

    Result<HashMap<String, Object>> getChildComment(Map<String, Object> params);
}
