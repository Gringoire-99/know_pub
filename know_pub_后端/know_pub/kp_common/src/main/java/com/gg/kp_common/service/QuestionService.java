package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.utils.Result;

public interface QuestionService extends IService<Question> {
    Result<QuestionVo> getDetail(String questionId);

    Result<Integer> postQuestion(Question question);
}
