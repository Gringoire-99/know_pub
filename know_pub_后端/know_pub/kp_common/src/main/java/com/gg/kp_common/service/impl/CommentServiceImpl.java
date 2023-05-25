package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.CommentMapper;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.SecurityUtils;
import com.gg.kp_common.utils.ValidationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    PostService postService;

    @Override
    public Result<HashMap<String, Object>> getPostComment(Map<String, Object> params) {

        /**
         * 查出所有根评论
         */
        String postId = (String) params.get("postId");
        ValidationUtils.validate().validateEmpty(postId);
        IPage<Comment> page = new PageUtils<Comment>().getPage(params);
        LambdaQueryWrapper<Comment> lqwN = new LambdaQueryWrapper<>();
        lqwN.eq(Comment::getPostId,postId);
        Long total = baseMapper.selectCount(lqwN);


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
                lqwC.eq(Comment::getRootCommentId, record.getId());
                comments.addAll(this.baseMapper.selectList(lqwC));
                lqwC.clear();
            }
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, comments);
        data.put(PageUtils.TOTAL, total);
        return Result.ok(data);
    }

    @Override
    public Result<Integer> postComment(CommentVo comment) {
//      TODO 对comment合法性进行校验

        String id = SecurityUtils.getId();
        Comment c = new Comment();
        c.setUserId(id);
        BeanUtils.copyProperties(comment, c);
        Integer r = postService.onComment(comment.getPostId());
        if (r == 0) throw new RuntimeException("评论失败-评论不存在");
        Integer result = this.baseMapper.insert(c);
        return Result.ok(result);
    }
}
