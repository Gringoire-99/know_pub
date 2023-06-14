package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.NewPost;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

public interface PostService extends IService<Post> {
    Result<Page<PostVo>> getRecommendedPosts(PageParams params);

    Integer onComment(String postId);

    Result<PostVo> getPost(String postId);

    Result<Integer> onLike(String postId);

    Result<Page<PostVo>> getDynamic(PageParams params, String userId);

    Result<Integer> addPost(NewPost post);

    Result<Page<PostVo>> getPosts(PageParams params, String questionId);
}
