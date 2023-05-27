package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.ActionMapper;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.entity.po.Action;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.po.Question;
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
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Result<Map<String, Object>> getRecommendedPosts(Map<String, Object> params) {
        IPage<Post> postIPage = this.baseMapper.selectPage(new PageUtils<Post>().getPage(params), null);
        List<Post> records = postIPage.getRecords();
        List<PostVo> recordsVo = BeanCopyUtils.copyBeanList(records, PostVo.class);
        Long total = postIPage.getTotal();
        setAction(recordsVo, null);
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, recordsVo);
        data.put(PageUtils.TOTAL, total);
        return Result.ok(data);
    }

    @Transactional
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
    public Result<Integer> onLike(Map<String, Object> params) {
//        先查询动作，如果有这个动作，说明是取消
        String postId = params.get("postId").toString();
        String userId = SecurityUtils.getId();
        ValidationUtils.validate().validateEmpty(postId, userId);
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

    @Override
    public Result<HashMap<String, Object>> getDynamic(Map<String, Object> params) {
        Object userId = params.get("userId");
        ValidationUtils.validate().validateEmpty(userId);
        LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
//        先从查出这个用户所有action,在根据action查出对应的post
        LambdaQueryWrapper<Action> lqwA = new LambdaQueryWrapper<>();
//        一个post只查出一个action
        lqwA.groupBy(Action::getTargetId)
                .eq(Action::getUserId, userId);

        IPage<Action> actionIPage = actionMapper.selectPage(new PageUtils<Action>().getPage(params), lqwA);
        List<Action> actions = actionIPage.getRecords();
//        将actions的postId提取为列表
        List<String> postIds = new ArrayList<>();
        actions.forEach(action -> postIds.add(action.getTargetId()));
        List<Post> posts = baseMapper.selectBatchIds(postIds);
        List<PostVo> postVos = BeanCopyUtils.copyBeanList(posts, PostVo.class);
        setAction(postVos, userId.toString());
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, postVos);
        data.put(PageUtils.TOTAL, actionIPage.getTotal());
        data.put(PageUtils.ROWS, actionIPage.getSize());
        return Result.ok(data);
    }

    @Transactional
    @Override
    public Result<Integer> addPost(Post post) {
//        从SecurityUtils中获取userId
        String userId = SecurityUtils.getId();
//        查询具体的question
        LambdaQueryWrapper<Question> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Question::getId, post.getQuestionId());
        Question question = questionMapper.selectOne(lqw);
        if (Objects.isNull(question)) {
            throw new SystemException("问题不存在");
        }
        post.setQuestion(question.getQuestion());
        post.setUserId(userId);
        post.setId(UUID.randomUUID().toString());
        int insert = this.baseMapper.insert(post);
        if (insert == 0) {
            throw new SystemException("发布失败");
        } else {
            //发布成功后，将问题的回答数+1
            LambdaUpdateWrapper<Question> luw = new LambdaUpdateWrapper<>();
            luw.eq(Question::getId, post.getQuestionId()).setSql("answer_count = answer_count + 1");
            questionMapper.update(null, luw);
        }
        return Result.ok(insert);
    }

    @Override
    public Result<Map<String, Object>> getPosts(Map<String, Object> params) {
        String questionId = (String) params.get("questionId");
        LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Post::getQuestionId, questionId);
        IPage<Post> postIPage = this.baseMapper.selectPage(new PageUtils<Post>().getPage(params), lqw);
        List<Post> records = postIPage.getRecords();
        List<PostVo> recordsVo = BeanCopyUtils.copyBeanList(records, PostVo.class);
        setAction(recordsVo, null);
        Long total = postIPage.getTotal();
        HashMap<String, Object> result = new HashMap<>();
        result.put(PageUtils.PAGE, recordsVo);
        result.put(PageUtils.TOTAL, total);
        result.put(PageUtils.ROWS, postIPage.getSize());
        return Result.ok(result);
    }

    /**
     * TODO 待重构，改为单action的模式
     */

    public void setAction(Collection<PostVo> posts, String userId) {
        if (userId == null) {
            try {
                userId = SecurityUtils.getId();
            } catch (Exception e) {
                return;
            }
        }
        LambdaQueryWrapper<Action> lqwA = new LambdaQueryWrapper<>();
//        查询出本用户userId和posts里所有postId的action
//        TODO 待优化
        String finalUserId = userId;
        posts.forEach(post -> {
//            查出post的所有动作
            lqwA.eq(Action::getUserId, finalUserId)
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
            lqwA.clear();
        });
    }

}
