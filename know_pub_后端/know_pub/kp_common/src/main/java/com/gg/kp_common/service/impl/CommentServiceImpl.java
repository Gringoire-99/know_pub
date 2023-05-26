package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.springframework.transaction.annotation.Transactional;

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
        lqwN.eq(Comment::getPostId, postId);
        Long total = baseMapper.selectCount(lqwN);


        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getPostId, postId).
                eq(Comment::getIsRootComment, true);
        IPage<Comment> commentIPage = this.baseMapper.selectPage(page, lqw);
        ArrayList<Comment> comments = new ArrayList<>(commentIPage.getRecords());
        long rootCommentTotal = commentIPage.getTotal();
        /*
          最多查出4个子评论
         */
        LambdaQueryWrapper<Comment> lqwC = new LambdaQueryWrapper<>();
        lqwC.orderByDesc(Comment::getLikeCount).orderByDesc(Comment::getCreateTime);
        for (Comment record : commentIPage.getRecords()) {
            lqwC.eq(Comment::getRootCommentId, record.getId()).eq(Comment::getIsRootComment, 0);
            lqwC.last("limit 4");
            comments.addAll(this.baseMapper.selectList(lqwC));
            lqwC.clear();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, comments);
        data.put(PageUtils.TOTAL, total);
        data.put(PageUtils.ROWS, comments.size());
        data.put("rootCommentTotal", rootCommentTotal);
        return Result.ok(data);
    }

    @Transactional
    @Override
    public Result<Integer> postComment(CommentVo comment) {
//      TODO 对comment合法性进行校验

        String id = SecurityUtils.getId();
        Comment c = new Comment();
        c.setUserId(id);
        BeanUtils.copyProperties(comment, c);
//        根据postId 更新 post的评论计数，如果r为0说明这个post不存在
        Integer r = postService.onComment(comment.getPostId());
        if (r == 0) throw new RuntimeException("评论失败-回复不存在");
        LambdaUpdateWrapper<Comment> lqwU = new LambdaUpdateWrapper<Comment>();
//        更新rootComment的childCount
        if (comment.getIsRootComment() == 0) {
            lqwU.eq(Comment::getId, comment.getRootCommentId())
                    .setSql("child_count = child_count + 1");
            baseMapper.update(null, lqwU);
        }
        Integer result = this.baseMapper.insert(c);
        return Result.ok(result);
    }

    @Override
    public Result<HashMap<String, Object>> getChildComment(Map<String, Object> params) {
//        根据commentId 查出所有子id
        String commentId = (String) params.get("commentId");
        ValidationUtils.validate().validateEmpty(commentId);
        IPage<Comment> page = new PageUtils<Comment>().getPage(params);
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getRootCommentId, commentId).eq(Comment::getIsRootComment, 0);
        IPage<Comment> commentIPage = this.baseMapper.selectPage(page, lqw);
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, commentIPage.getRecords());
        data.put(PageUtils.TOTAL, commentIPage.getTotal());
        data.put(PageUtils.ROWS, commentIPage.getRecords().size());
        return Result.ok(data);

    }

    @Transactional
    @Override
    public Result<Integer> likeComment(Map<String, Object> params) {
        String commentId = (String) params.get("commentId");
        LambdaQueryWrapper<Comment> lqwC = new LambdaQueryWrapper<>();
        lqwC.eq(Comment::getId, commentId);
        Comment comment = this.baseMapper.selectOne(lqwC);
        if (comment == null) throw new RuntimeException("评论不存在");
        String userId = SecurityUtils.getId();

        Integer result = baseMapper.isLiked(commentId, userId);
        if (result == 0) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            baseMapper.like(commentId, userId);
            baseMapper.update(comment, lqwC);
            return Result.ok(1);
        } else if (result == 1) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            baseMapper.unlike(commentId, userId);
            baseMapper.update(comment, lqwC);
            return Result.ok(0);
        } else {
            throw new RuntimeException("未知错误");
        }
    }

}
