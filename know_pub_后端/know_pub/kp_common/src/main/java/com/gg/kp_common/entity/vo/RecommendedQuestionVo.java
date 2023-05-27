package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedQuestionVo {
    private String id;
    private String question;
    private String content;
    private int answerCount;
}
