package com.gg.kp_main.controller;

import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    public PostService postService;
    /**
     *根据id获取推荐的帖子
     */
    @GetMapping("/recommended-posts")
    public Result<Map<String,Object>> recommendedPost(@RequestParam Map<String,Object> params){

        return postService.getRecommendedPosts(params);
    }
    @GetMapping("/getPost")
    public Result<PostVo> getPost(@RequestParam String postId){
        return postService.getPost(postId);
    }
}
