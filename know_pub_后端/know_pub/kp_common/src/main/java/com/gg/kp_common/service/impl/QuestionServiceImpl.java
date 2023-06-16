package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.entity.vo.RecommendedQuestionVo;
import com.gg.kp_common.entity.vo.save.PostQuestion;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Override
    public Result<QuestionVo> getDetail(String questionId) {
        QuestionVo question = this.baseMapper.getQuestionDetail(questionId);
        if (Objects.isNull(question)) throw new SystemException("问题不存在");
        setAnonymity(Collections.singletonList(question));
        return Result.ok(question);
    }


    @Transactional
    @Override
    public Result<Integer> postQuestion(PostQuestion question) {
        int result;
        Question newQuestion = new Question();
        BeanUtils.copyProperties(question, newQuestion);
        String userId = SecurityUtils.getId();
        newQuestion.setUserId(userId);
        result = this.baseMapper.insert(newQuestion);

        return Result.ok(result);
    }

    @Override
    public Result<Page<QuestionVo>> getQuestions(PageParams params) {
        IPage<QuestionVo> questionVoPage = this.baseMapper.getQuestionVoPage(new PageUtils<QuestionVo>().getPage(params));
        List<QuestionVo> records = questionVoPage.getRecords();
        setAnonymity(records);
        long total = questionVoPage.getTotal();
        long rows = records.size();
        Page<QuestionVo> page = new Page<>(total, rows, records);
        return Result.ok(page);
    }

    @Override
    public Result<List<RecommendedQuestionVo>> getRecommendedQuestion(String questionId) {
        Question question = this.baseMapper.selectById(questionId);
        if (Objects.isNull(question)) {
            throw new SystemException("问题不存在");
        }
        String tags = question.getTagNames();
        String[] tagNames = tags.split("\\+");
        LambdaQueryWrapper<Question> lqw = new LambdaQueryWrapper<>();
        lqw.ne(Question::getId, questionId);

        lqw.and(qw -> {
            for (String tagName : tagNames) {
                qw.like(Question::getTagNames, tagName).or();
            }
        });
        lqw.last("LIMIT 5");
        List<Question> questions = this.baseMapper.selectList(lqw);
        List<RecommendedQuestionVo> result = BeanCopyUtils.copyBeanList(questions, RecommendedQuestionVo.class);
        return Result.ok(result);
    }


    public void setAnonymity(List<QuestionVo> questionVos) {
        questionVos.forEach(questionVo -> {
            if (questionVo.getIsAnonymous() == EntityConstant.IS_ANONYMOUS) {
                questionVo.setUserId("");
                questionVo.setName("匿名用户");
                questionVo.setAvatar("");
            }
        });
    }
}
