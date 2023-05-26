package com.gg.kp_main.controller;

import com.gg.kp_common.entity.vo.CommentVo;
import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RequestMapping("/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/comments")
    public Result<HashMap<String, Object>> getPostComment(@RequestParam Map<String,Object> params){
        return commentService.getPostComment(params);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/post-comment")
    public Result<Integer> postComment(@RequestBody CommentVo comment){
        return commentService.postComment(comment);
    }

    @GetMapping("/child-comments")
    public Result<HashMap<String, Object>> getChildComment(@RequestParam Map<String,Object> params){
        return commentService.getChildComment(params);
    }
    @PostMapping("/like")
    public Result<Integer> likeComment(@RequestBody Map<String,Object> params){
        return commentService.likeComment(params);
    }


}
