package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.Result;
import com.gg.kp_common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
}
