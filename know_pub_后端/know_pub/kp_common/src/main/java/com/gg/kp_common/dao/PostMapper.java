package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    IPage<PostVo> getRecommendedPosts(@Param("page") IPage<PostVo> page, @Param("userId") String userId);

    IPage<PostVo> getDynamic(IPage<PostVo> page, String userId);

    IPage<PostVo> getPosts(IPage<Post> page, String questionId, String userId);

    List<PostVo> getPostsByIds(List<String> list, String userId);
}
