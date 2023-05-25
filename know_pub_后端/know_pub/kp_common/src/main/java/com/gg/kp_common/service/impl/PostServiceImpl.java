package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.BeanCopyUtils;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Result<Map<String,Object>> getRecommendedPosts(Map<String, Object> params) {
        IPage<Post> postIPage = this.baseMapper.selectPage(new PageUtils<Post>().getPage(params), null);
        List<Post> records = postIPage.getRecords();
        List<PostVo> recordsVo = BeanCopyUtils.copyBeanList(records,PostVo.class);
        Long total = postIPage.getTotal();
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, recordsVo);
        data.put(PageUtils.TOTAL,total);
        return Result.ok(data);
    }

    @Override
    public Integer onComment(String postId) {
        LambdaUpdateWrapper<Post> luw = new LambdaUpdateWrapper<>();
        luw.eq(Post::getId,postId).setSql("comment_count = comment_count + 1");
        return baseMapper.update(null,luw);
    }

    @Override
    public Result<PostVo> getPost(String postId) {
        Post post = getById(postId);
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post,postVo);
        return Result.ok(postVo);
    }

}
