package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.ActionMapper;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Action;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.entity.vo.save.NewPost;
import com.gg.kp_common.service.ActionService;
import com.gg.kp_common.service.PostService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    ActionService actionService;
    @Autowired
    private ActionMapper actionMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据userId获取推荐博文,如果userId不为空，进行连表查询设置博文的状态（点赞，点踩，收藏，回复）
     * 如果userId为空，只查询博文
     *
     * @param params 分页参数
     * @return 推荐博文
     */
    @Override
    public Result<Page<PostVo>> getRecommendedPosts(PageParams params) {
        String userId = SecurityUtils.getId();
        List<PostVo> record;
        long total;
        long rows;
        if (!Objects.isNull(userId)) {
            IPage<PostVo> p = new PageUtils<PostVo>().getPage(params);
            IPage<PostVo> recommendedPosts = this.baseMapper.getRecommendedPosts(p, userId);
            record = recommendedPosts.getRecords();
            total = recommendedPosts.getTotal();
            rows = record.size();
        } else {
            LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Post::getStatus, EntityConstant.PUBLISHED);
            IPage<Post> p = new PageUtils<Post>().getPage(params);
            IPage<Post> postIPage = this.baseMapper.selectPage(p, lqw);
            List<Post> posts = postIPage.getRecords();
            record = BeanCopyUtils.copyBeanList(posts, PostVo.class);
            total = postIPage.getTotal();
            rows = postIPage.getSize();
        }
        Page<PostVo> page = new Page<>(total, rows, record);
        return Result.ok(page);
    }

    /**
     * 当博文被回复时，更新博文的回复数
     *
     * @param postId 博文id
     * @return 更新结果
     */
    @Transactional
    @Override
    public Integer onComment(String postId) {
        String userId = SecurityUtils.getId();
        LambdaUpdateWrapper<Post> luw = new LambdaUpdateWrapper<>();
        luw.eq(Post::getId, postId).setSql("comment_count = comment_count + 1");
        int result = baseMapper.update(null, luw);

        LambdaQueryWrapper<Action> pALqw = new LambdaQueryWrapper<>();
        pALqw.eq(Action::getTargetId, postId).eq(Action::getUserId, userId);
        Action action = this.actionMapper.selectOne(pALqw);
        if (Objects.isNull(action)) {
            action = new Action();
        }
        action.setUserId(userId);
        action.setTargetId(postId);
        action.setReplied(EntityConstant.ACTION_ON);

        this.actionService.saveOrUpdate(action);
        return result;
    }

    /**
     * 获取某个博文的详细信息
     *
     * @param postId 博文id
     * @return 博文详细信息
     */
    @Override
    public Result<PostVo> getPost(String postId) {
        Post post = getById(postId);
        if (Objects.isNull(post)) {
            throw new SystemException("博文不存在");
        }
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post, postVo);
        return Result.ok(postVo);
    }

    /**
     * 博文被点赞时，更新博文的点赞数
     * 如果已经点赞，取消点赞
     *
     * @param postId 博文id
     * @return 更新结果(1点赞 ， 0取消点赞)
     */

    @Transactional
    @Override
    public Result<Integer> onLike(String postId) {
        String userId = SecurityUtils.getId();
        Post post = baseMapper.selectById(postId);
        int result;

//       判断博文是否存在
        if (Objects.isNull(post)) {
            throw new SystemException("博文不存在");
        }
        ValidationUtils.validate().validateEmpty(postId, userId);

        LambdaQueryWrapper<Action> lqwA = new LambdaQueryWrapper<>();
        lqwA.eq(Action::getTargetId, postId)
                .eq(Action::getUserId, userId);
        Action action = actionMapper.selectOne(lqwA);

//      判断该用户是否已存在对该博文的动作know_pub
        if (Objects.isNull(action)) {
            Action newAction = new Action();
            newAction.setLiked(EntityConstant.ACTION_ON);
            newAction.setUserId(userId);
            newAction.setTargetId(postId);
            actionMapper.insert(newAction);
            result = EntityConstant.ACTION_ON;
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
//            点赞动作的开关
            result = action.getLiked() == EntityConstant.ACTION_ON ? EntityConstant.ACTION_OFF : EntityConstant.ACTION_ON;
            action.setLiked(result);
            post.setLikeCount(post.getLikeCount() + (result == EntityConstant.ACTION_ON ? 1 : -1));
//            点赞和点踩是互斥操作
            if (action.getDisliked() == EntityConstant.ACTION_ON) {
                action.setDisliked(EntityConstant.ACTION_OFF);
                post.setDislikeCount(post.getDislikeCount() - 1);
            }
            actionMapper.update(action, lqwA);

        }
        baseMapper.updateById(post);

        return Result.ok(result);
    }

    /**
     * 根据userId获取博文动态(查看个人主页时触发)
     *
     * @param params 分页参数
     * @param userId 用户id
     * @return 博文动态
     */
    @Override
    public Result<Page<PostVo>> getDynamic(PageParams params, String userId) {
        ValidationUtils.validate().validateEmpty(userId);
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new SystemException("用户不存在");
        }

        IPage<PostVo> page1 = new PageUtils<PostVo>().getPage(params);
        IPage<PostVo> page2 = this.baseMapper.getDynamic(page1, userId);
        Page<PostVo> page = new Page<>(page2.getTotal(), page2.getSize(), page2.getRecords());
        return Result.ok(page);
    }

    /**
     * 发布博文
     *
     * @param post 博文
     * @return 发布结果
     */
    @Transactional
    @Override
    public Result<Integer> addPost(NewPost post) {
//        从SecurityUtils中获取userId
        String userId = SecurityUtils.getId();
//        查询具体的question
        LambdaQueryWrapper<Question> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Question::getId, post.getQuestionId());
        Question question = questionMapper.selectOne(lqw);
        if (Objects.isNull(question)) {
            throw new SystemException("问题不存在");
        }
        Post newPost = new Post();
        BeanUtils.copyProperties(post, newPost);

        newPost.setQuestion(question.getQuestion());
        newPost.setUserId(userId);
        newPost.setId(UUID.randomUUID().toString());
        int insert = this.baseMapper.insert(newPost);
        if (insert == 0) {
            throw new SystemException("发布失败");
        } else {
            //发布成功后，将问题的回答数+1
            LambdaUpdateWrapper<Question> luw = new LambdaUpdateWrapper<>();
            luw.eq(Question::getId, newPost.getQuestionId()).setSql("answer_count = answer_count + 1");
            questionMapper.update(null, luw);
        }
        return Result.ok(insert);
    }

    /**
     * 获取某个问题下的所有博文
     *
     * @param params     分页参数
     * @param questionId 问题id
     * @return 博文列表
     */
    @Override
    public Result<Page<PostVo>> getPosts(PageParams params, String questionId) {
        Question question = questionMapper.selectById(questionId);
        if (Objects.isNull(question)) {
            throw new SystemException("问题不存在");
        }
        String userId = SecurityUtils.getId();
        List<PostVo> records;
        long total;
        long rows;
        IPage<Post> page = new PageUtils<Post>().getPage(params);
        if (Objects.isNull(userId)) {
            LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Post::getQuestionId, questionId);
            IPage<Post> postIPage = this.baseMapper.selectPage(page, lqw);
            List<Post> posts = postIPage.getRecords();
            records = BeanCopyUtils.copyBeanList(posts, PostVo.class);
            total = postIPage.getTotal();
            rows = records.size();
        } else {
            IPage<PostVo> iPage = this.baseMapper.getPosts(page, questionId, userId);
            records = iPage.getRecords();
            total = iPage.getTotal();
            rows = records.size();
        }
        Page<PostVo> pageData = new Page<>(total, rows, records);
        return Result.ok(pageData);
    }


}
