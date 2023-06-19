package com.gg.kp_common.utils.scheduler;

import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.service.QuestionService;
import com.gg.kp_common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateQuestionViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private QuestionService questionService;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateQuestionViewCount(){
        Map<String, Object> questionViewCount = redisCache.getCacheMap("questionViewCount");
        List<Question> collect = questionViewCount.entrySet().stream().map(entry -> new Question(entry.getKey(), (Integer) entry.getValue())).collect(Collectors.toList());
        questionService.updateBatchById(collect);
    }
}
