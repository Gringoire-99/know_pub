package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.CommentMapper;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Comment;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.entity.vo.PostComment;
import com.gg.kp_common.entity.vo.RootCommentVo;
import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    PostService postService;
    @Autowired
    PostMapper postMapper;

    /**
     * 根据postId获取博文的评论
     *
     * @param params 分页参数
     * @param postId 博文id
     * @return 博文的评论
     */
    @Override
    public Result<Page<RootCommentVo>> getPostComment(PageParams params, String postId) {
        /*
          查出所有根评论
         */
        ValidationUtils.validate().validateEmpty(postId);
        Post post = postMapper.selectById(postId);
        if (Objects.isNull(post)) {
            throw new SystemException("博文不存在");
        }
        long total;
        long rows;
        List<RootCommentVo> pageData;

        IPage<Comment> page = new PageUtils<Comment>().getPage(params);
        IPage<RootCommentVo> commentIPage = this.baseMapper.commentPage(page, postId);
        total = commentIPage.getTotal();
        rows = commentIPage.getSize();
        pageData = commentIPage.getRecords();
        /*
          查出根评论的子评论，最多查出4个子评论
         */
        for (RootCommentVo rootCommentVo : pageData) {
            List<CommentVo> comments = this.baseMapper.getCommentChildren(rootCommentVo.getId());
            rootCommentVo.setChildComments(comments);
        }

        Page<RootCommentVo> rootCommentVoPage = new Page<>(total, rows, pageData);
        return Result.ok(rootCommentVoPage);
    }

    /**
     * 发布评论
     *
     * @param comment 被发布评论
     * @return 更新结果
     */
    @Transactional
    @Override
    public Result<Integer> postComment(PostComment comment) {
//      对comment合法性进行校验
        String postId = comment.getPostId();
        Post post = postMapper.selectById(postId);
        if (Objects.isNull(post)) {
            throw new SystemException("博文不存在");
        }

        Comment rootComment;
        if (comment.getIsRootComment() == EntityConstant.NOT_ROOT_COMMENT) {
            rootComment = this.baseMapper.selectById(comment.getRootCommentId());

            if (Objects.isNull(rootComment)) {
                throw new SystemException("回复的评论不存在");
            }
            if (!Objects.isNull(comment.getReplyToCommentId())) {
                Comment replyToComment = this.baseMapper.selectById(comment.getReplyToCommentId());
                if (Objects.isNull(replyToComment)) {
                    throw new SystemException("回复的评论不存在");
                }
            }
            rootComment.setChildCount(rootComment.getChildCount() + 1);
            baseMapper.updateById(rootComment);

        }


        Comment c = new Comment();
        BeanUtils.copyProperties(comment, c);
        String id = SecurityUtils.getId();
        c.setUserId(id);
        postService.onComment(post.getId());
        boolean result = this.save(c);
        return Result.ok(result ? 1 : 0);
    }

    /**
     * 根据commentId获取子评论
     *
     * @param params    分页参数
     * @param commentId 评论id
     * @return 子评论
     */
    @Override
    public Result<Page<CommentVo>> getChildComment(PageParams params, String commentId) {
//        根据commentId 查出所有子id
        ValidationUtils.validate().validateEmpty(commentId);

        Comment comment = baseMapper.selectById(commentId);
        if (Objects.isNull(comment)) {
            throw new SystemException("评论不存在");
        }

        IPage<Comment> page = new PageUtils<Comment>().getPage(params);
        IPage<CommentVo> pageData = this.baseMapper.getChildrenComment(page, commentId);
        Page<CommentVo> commentVoPage = new Page<CommentVo>(pageData.getTotal(), pageData.getSize(), pageData.getRecords());
        return Result.ok(commentVoPage);

    }

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     * @return 点赞结果
     */
    @Transactional
    @Override
    public Result<Integer> onLike(String commentId) {
        LambdaQueryWrapper<Comment> lqwC = new LambdaQueryWrapper<>();
        lqwC.eq(Comment::getId, commentId);
        Comment comment = this.baseMapper.selectOne(lqwC);
        if (comment == null) throw new RuntimeException("评论不存在");
        String userId = SecurityUtils.getId();

        Integer result = baseMapper.isLiked(commentId, userId);
        if (result == EntityConstant.ACTION_OFF) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            baseMapper.like(commentId, userId);
            baseMapper.update(comment, lqwC);
            return Result.ok(EntityConstant.ACTION_ON);
        } else if (result == EntityConstant.ACTION_ON) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            baseMapper.unlike(commentId, userId);
            baseMapper.update(comment, lqwC);
            return Result.ok(EntityConstant.ACTION_OFF);
        } else {
            throw new RuntimeException("未知错误");
        }
    }

}
