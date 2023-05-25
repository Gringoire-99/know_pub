package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.utils.Result;

import java.util.List;
import java.util.Map;

public interface PostService extends IService<Post> {
    Result<Map<String,Object>> getRecommendedPosts(Map<String, Object> params);

    Integer onComment(String postId);

    Result<PostVo> getPost(String postId);
}
