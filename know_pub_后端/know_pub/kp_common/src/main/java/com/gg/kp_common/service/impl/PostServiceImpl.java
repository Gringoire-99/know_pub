package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.PostActionMapper;
import com.gg.kp_common.dao.PostMapper;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.dao.UserMapper;
import com.gg.kp_common.entity.po.Post;
import com.gg.kp_common.entity.po.PostAction;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.NewPost;
import com.gg.kp_common.entity.vo.PostVo;
import com.gg.kp_common.service.PostActionService;
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
    private PostActionMapper postActionMapper;
    @Autowired
    PostActionService postActionService;
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
    public Result<Map<String, Object>> getRecommendedPosts(PageParams params) {
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
            IPage<Post> p = new PageUtils<Post>().getPage(params);
            IPage<Post> postIPage = this.baseMapper.selectPage(p, null);
            List<Post> posts = postIPage.getRecords();
            record = BeanCopyUtils.copyBeanList(posts, PostVo.class);
            total = postIPage.getTotal();
            rows = postIPage.getSize();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, record);
        data.put(PageUtils.TOTAL, total);
        data.put(PageUtils.ROWS, rows);
        return Result.ok(data);
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
        LambdaUpdateWrapper<Post> luw = new LambdaUpdateWrapper<>();
        luw.eq(Post::getId, postId).setSql("comment_count = comment_count + 1");
        return baseMapper.update(null, luw);
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

        LambdaQueryWrapper<PostAction> lqwA = new LambdaQueryWrapper<>();
        lqwA.eq(PostAction::getTargetId, postId)
                .eq(PostAction::getUserId, userId);
        PostAction postAction = postActionMapper.selectOne(lqwA);

//      判断该用户是否已存在对该博文的动作
        if (Objects.isNull(postAction)) {
            PostAction newAction = new PostAction();
            newAction.setLiked(EntityConstant.ACTION_ON);
            newAction.setUserId(userId);
            newAction.setTargetId(postId);
            postActionMapper.insert(newAction);
            result = EntityConstant.ACTION_ON;
        } else {
//            点赞动作的开关
            result = postAction.getLiked() == EntityConstant.ACTION_ON ? EntityConstant.ACTION_OFF : EntityConstant.ACTION_ON;
            postAction.setLiked(result);

//            点赞和点踩是互斥操作
            if (postAction.getLiked() == EntityConstant.ACTION_ON) {
                postAction.setDisliked(EntityConstant.ACTION_OFF);
            }

        }
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
    public Result<HashMap<String, Object>> getDynamic(PageParams params, String userId) {
        ValidationUtils.validate().validateEmpty(userId);
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            throw new SystemException("用户不存在");
        }

        LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
        IPage<PostVo> page = this.baseMapper.getDynamic(lqw, userId);

        HashMap<String, Object> data = new HashMap<>();
        data.put(PageUtils.PAGE, page.getRecords());
        data.put(PageUtils.TOTAL, page.getTotal());
        data.put(PageUtils.ROWS, page.getRecords().size());
        return Result.ok(data);
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

    /*
        在问题详情页里查询问题的答案
     */
    @Override
    public Result<Map<String, Object>> getPosts(PageParams params, String questionId) {
        Question question = questionMapper.selectById(questionId);
        if (Objects.isNull(question)) {
            throw new SystemException("问题不存在");
        }
        String userId = SecurityUtils.getId();
        if (Objects.isNull(userId)) {
            LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Post::getQuestionId, questionId);
            IPage<Post> postIPage = this.baseMapper.selectPage(new PageUtils<Post>().getPage(params), lqw);
            List<Post> records = postIPage.getRecords();
            List<PostVo> recordsVo = BeanCopyUtils.copyBeanList(records, PostVo.class);
            Long total = postIPage.getTotal();
            HashMap<String, Object> result = new HashMap<>();
            result.put(PageUtils.PAGE, recordsVo);
            result.put(PageUtils.TOTAL, total);
            result.put(PageUtils.ROWS, postIPage.getSize());
            return Result.ok(result);
        } else {
            return null;
        }

    }


}
