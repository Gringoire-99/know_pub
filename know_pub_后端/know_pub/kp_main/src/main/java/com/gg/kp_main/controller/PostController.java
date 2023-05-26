package com.gg.kp_main.controller;

import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @PostMapping("/like")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> onLike(@RequestBody Map<String,Object> params){
        return postService.onLike(params);
    }

    @GetMapping("/dynamic")
    public Result<HashMap<String, Object>> getDynamic(@RequestParam Map<String,Object> params){
        return postService.getDynamic(params);
    }

}
