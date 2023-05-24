package com.gg.kp_main.controller;

import com.gg.kp_common.service.CommentService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/comment/post-comment")
    public Result<HashMap<String, Object>> getPostComment(@RequestParam Map<String,Object> params){
        return commentService.getPostComment(params);
    }


}
