package com.gg.kp_main.controller;

import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.entity.vo.QuestionVo;
import com.gg.kp_common.entity.vo.RecommendedQuestionVo;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/detail")
    public Result<QuestionVo> getQuestionDetail(@RequestParam String questionId) {
        return questionService.getDetail(questionId);
    }

    @PostMapping("/postQuestion")
    @PreAuthorize("hasRole('USER')")
    public Result<Integer> postQuestion(@RequestBody Question question) {
        return questionService.postQuestion(question);
    }

    @GetMapping("/questions")
    public Result<Map<String, Object>> getQuestions(@RequestParam Map<String, Object> params) {
        return questionService.getQuestions(params);
    }

    @GetMapping("/recommended-question")
    public Result<List<RecommendedQuestionVo>> getRecommendedQuestion(@RequestParam String questionId) {
        return questionService.getRecommendedQuestion(questionId);

    }
}
