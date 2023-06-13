package com.gg.kp_common.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    IPage<PostVo> getRecommendedPosts(@Param("page") IPage<PostVo> page, @Param("userId") String userId);

    IPage<PostVo> getDynamic(LambdaQueryWrapper<Post> lqw, String userId);
}
