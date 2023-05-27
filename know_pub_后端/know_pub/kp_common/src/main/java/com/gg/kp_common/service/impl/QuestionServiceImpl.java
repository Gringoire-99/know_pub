package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.entity.vo.RecommendedQuestionVo;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.BeanCopyUtils;
import com.gg.kp_common.utils.PageUtils;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Override
    public Result<QuestionVo> getDetail(String questionId) {
        Question question = this.baseMapper.selectById(questionId);
        if (Objects.isNull(question)) throw new SystemException("问题不存在");
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        return Result.ok(questionVo);
    }

    @Transactional
    @Override
    public Result<Integer> postQuestion(Question question) {
        int result;
        String userId = SecurityUtils.getId();
        question.setUserId(userId);
        result = this.baseMapper.insert(question);

        return Result.ok(result);
    }

    @Override
    public Result<Map<String, Object>> getQuestions(Map<String, Object> params) {
        IPage<QuestionVo> questionVoPage = this.baseMapper.getQuestionVoPage(new PageUtils<QuestionVo>().getPage(params));
        List<QuestionVo> records = questionVoPage.getRecords();
        setAnonymity(records);
        long total = questionVoPage.getTotal();
        long rows = records.size();
        HashMap<String, Object> result = new HashMap<>();
        result.put(PageUtils.PAGE, records);
        result.put(PageUtils.TOTAL, total);
        result.put(PageUtils.ROWS, rows);
        return Result.ok(result);
    }

    @Override
    public Result<List<RecommendedQuestionVo>> getRecommendedQuestion(String questionId) {
        Question question = this.baseMapper.selectById(questionId);
        String tags = question.getTagNames();
        String[] tagNames = tags.split("\\+");
        LambdaQueryWrapper<Question> lqw = new LambdaQueryWrapper<>();
        for (String tagName : tagNames) {
            lqw.or().like(Question::getTagNames, tagName);
        }
        lqw.last("LIMIT 3");
        List<Question> questions = this.baseMapper.selectList(lqw);
        List<RecommendedQuestionVo> result = BeanCopyUtils.copyBeanList(questions, RecommendedQuestionVo.class);
        return Result.ok(result);
    }


    public void setAnonymity(Collection<QuestionVo> questionVos) {
        questionVos.forEach(questionVo -> {
            if (questionVo.getIsAnonymous() == 1) {
                questionVo.setUserId(null);
                questionVo.setName("匿名用户");
            }
        });
    }
}
