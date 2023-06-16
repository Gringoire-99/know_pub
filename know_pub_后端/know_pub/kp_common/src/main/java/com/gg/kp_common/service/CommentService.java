package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.entity.vo.RootCommentVo;
import com.gg.kp_common.entity.vo.save.PostComment;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

public interface CommentService extends IService<Comment> {
    Result<Page<RootCommentVo>> getPostComment(PageParams params, String postId);

    Result<Integer> postComment(PostComment comment);

    Result<Page<CommentVo>> getChildComment(PageParams params, String commentId);

    Result<Integer> onLike(String commentId);
}
