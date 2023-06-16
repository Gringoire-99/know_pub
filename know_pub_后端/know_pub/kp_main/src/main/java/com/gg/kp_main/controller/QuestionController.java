package com.gg.kp_main.controller;

import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.entity.vo.RecommendedQuestionVo;
import com.gg.kp_common.entity.vo.save.PostQuestion;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/detail")
    public Result<QuestionVo> getQuestionDetail(@RequestParam String questionId) {
        return questionService.getDetail(questionId);
    }

    @PostMapping("/post-question")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> postQuestion(@RequestBody PostQuestion question) {
        return questionService.postQuestion(question);
    }

    @GetMapping("/questions")
    public Result<Page<QuestionVo>> getQuestions(@ApiParam PageParams params) {
        return questionService.getQuestions(params);
    }


    /**
     * 根据某个问题，获取推荐问题（具有相同tag）
     * @param questionId 问题id
     * @return 推荐问题
     */
    @GetMapping("/recommended-question")
    public Result<List<RecommendedQuestionVo>> getRecommendedQuestion(@RequestParam String questionId) {
        return questionService.getRecommendedQuestion(questionId);

    }
}
