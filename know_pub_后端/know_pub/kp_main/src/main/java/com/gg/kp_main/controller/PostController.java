package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.NewPost;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    public PostService postService;

    @GetMapping("/recommended-posts")
    public Result<Page<PostVo>> recommendedPost(@ApiParam PageParams params) {
        return postService.getRecommendedPosts(params);
    }

    @GetMapping("/get-post")
    public Result<PostVo> getPost(@RequestParam String postId) {
        return postService.getPost(postId);
    }

    @PostMapping("/like")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> onLike(@RequestParam String postId) {
        return postService.onLike(postId);
    }

    @GetMapping("/dynamic")
    public Result<Page<PostVo>> getDynamic(@ApiParam PageParams params,
                                           @RequestParam String userId) {
        return postService.getDynamic(params, userId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-post")
    public Result<Integer> addPost(@RequestBody NewPost post) {
        return postService.addPost(post);
    }

    @GetMapping("/get-posts")
    public Result<Page<PostVo>> getAnswer(@ApiParam PageParams params,
                                          @RequestParam String questionId) {
        return postService.getPosts(params, questionId);
    }

}
