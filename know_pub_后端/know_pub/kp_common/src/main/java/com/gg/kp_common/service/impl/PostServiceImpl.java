package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.dao.ActionMapper;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.entity.po.Action;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private ActionMapper actionMapper;

    @Override
    public Result<Map<String, Object>> getRecommendedPosts(Map<String, Object> params) {
        IPage<Post> postIPage = this.baseMapper.selectPage(new PageUtils<Post>().getPage(params), null);
        List<Post> records = postIPage.getRecords();
        List<PostVo> recordsVo = BeanCopyUtils.copyBeanList(records, PostVo.class);
        Long total = postIPage.getTotal();
        setAction(recordsVo);
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, recordsVo);
        data.put(PageUtils.TOTAL, total);
        return Result.ok(data);
    }

    @Override
    public Integer onComment(String postId) {
        LambdaUpdateWrapper<Post> luw = new LambdaUpdateWrapper<>();
        luw.eq(Post::getId, postId).setSql("comment_count = comment_count + 1");
        return baseMapper.update(null, luw);
    }

    @Override
    public Result<PostVo> getPost(String postId) {
        Post post = getById(postId);
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post, postVo);
        return Result.ok(postVo);
    }

    @Transactional
    @Override
    public Result<Integer> onLike(Map<String,Object> params) {
//        先查询动作，如果有这个动作，说明是取消
        String postId = params.get("postId").toString();
        String userId = SecurityUtils.getId();
        ValidationUtils.validate().validateEmpty(postId,userId);
        LambdaQueryWrapper<Action> lqwA = new LambdaQueryWrapper<>();
        lqwA.eq(Action::getTargetId, postId)
                .eq(Action::getUserId, userId);
        Action a = actionMapper.selectOne(lqwA);
        LambdaUpdateWrapper<Post> luw = new LambdaUpdateWrapper<>();
        luw.eq(Post::getId, postId);
        if (Objects.isNull(a)) {
//            创建新动作，like++
            luw.setSql("like_count = like_count + 1");
            Action action = new Action();
            action.setActionId(UUID.randomUUID().toString());
            action.setAction(Action.LIKE);
            action.setUserId(userId);
            action.setTargetId(postId);
            actionMapper.insert(action);
        } else {
//            删除动作，like--
            actionMapper.delete(lqwA);
            luw.setSql("like_count = like_count - 1");
        }
        return Result.ok(baseMapper.update(null, luw));
    }

    public void setAction(Collection<PostVo> posts) {
        String userId;
        try {
            userId = SecurityUtils.getId();
        } catch (Exception e) {
            return;
        }
        LambdaQueryWrapper<Action> lqwA = new LambdaQueryWrapper<>();
//        查询出本用户userId和posts里所有postId的action
//        TODO 待优化
        posts.forEach(post -> {
//            查出post的所有动作
            lqwA.eq(Action::getUserId, userId)
                    .eq(Action::getTargetId, post.getId());
            List<Action> actions = actionMapper.selectList(lqwA);
//            根据修改post的状态
            actions.forEach(action -> {
                String name = action.getAction();
                if (name.equals(Action.LIKE)) {
                    post.setLiked(true);
                } else if (name.equals(Action.COLLECTION)) {
                    post.setCollected(true);
                }
            });
        });
    }

}
