package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.entity.vo.PostComment;
import com.gg.kp_common.entity.vo.RootCommentVo;
import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public Result<Page<RootCommentVo>> getPostComment(@ApiParam PageParams params, @RequestParam String postId) {
        return commentService.getPostComment(params, postId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/post-comment")
    public Result<Integer> postComment(@RequestBody PostComment comment) {
        return commentService.postComment(comment);
    }

    @GetMapping("/child-comments")
    public Result<Page<CommentVo>> getChildComment(@ApiParam PageParams params, @RequestParam String commentId) {
        return commentService.getChildComment(params, commentId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/like")
    public Result<Integer> likeComment(@RequestParam String commentId) {
        return commentService.onLike(commentId);
    }


}
