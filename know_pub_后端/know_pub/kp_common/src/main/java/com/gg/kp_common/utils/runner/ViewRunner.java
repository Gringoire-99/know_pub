package com.gg.kp_common.utils.runner;

import com.gg.kp_common.dao.QuestionMapper;
import com.gg.kp_common.entity.po.Question;
import com.gg.kp_common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ViewRunner implements CommandLineRunner {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Question> questions = questionMapper.selectList(null);
        HashMap<String, Integer> viewMap = new HashMap<>();
        questions.forEach(
                q -> viewMap.put(q.getId(), q.getViewCount())
        );
        redisCache.setCacheMap("questionViewCount",viewMap);
    }
}
