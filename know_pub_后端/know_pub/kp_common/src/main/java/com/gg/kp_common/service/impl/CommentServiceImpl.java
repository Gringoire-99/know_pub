package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.CommentMapper;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public Result<HashMap<String, Object>> getPostComment(Map<String, Object> params) {

        /**
         * 查出所有根评论
         */
        String postId = (String) params.get("postId");
        ValidationUtils.validate().validateEmpty(postId);
        IPage<Comment> page = new PageUtils<Comment>().getPage(params);
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getPostId, postId).
                eq(Comment::getIsRootComment, true);
        IPage<Comment> commentIPage = this.baseMapper.selectPage(page, lqw);
        ArrayList<Comment> comments = new ArrayList<>(commentIPage.getRecords());
        /*
          如果根评论的子评论数量小于4，则根据根评论查出所有根评论的子评论
         */
        LambdaQueryWrapper<Comment> lqwC = new LambdaQueryWrapper<>();
        for (Comment record : commentIPage.getRecords()) {
            if (record.getChildCount() < 4) {
                lqw.eq(Comment::getId, record.getParentId());
                comments.addAll(this.baseMapper.selectList(lqwC));
            }
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE,comments);
        data.put(PageUtils.TOTAL,commentIPage.getTotal());
        return Result.ok(data);
    }
}
