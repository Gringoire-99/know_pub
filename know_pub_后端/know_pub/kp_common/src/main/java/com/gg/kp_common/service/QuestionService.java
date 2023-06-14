package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.PostQuestionVo;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.entity.vo.RecommendedQuestionVo;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

import java.util.List;
import java.util.Map;

public interface QuestionService extends IService<Question> {
    Result<QuestionVo> getDetail(String questionId);

    Result<Integer> postQuestion(PostQuestionVo question);

    Result<Page<QuestionVo>> getQuestions(PageParams params);

    Result<List<RecommendedQuestionVo>> getRecommendedQuestion(String questionId);

}
