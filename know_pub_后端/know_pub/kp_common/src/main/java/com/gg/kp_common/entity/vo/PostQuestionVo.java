package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionVo {
    private String question;
    private String content;
    private String cover;
    private String images;
    private String tagNames;
    private Integer isAnonymous;
}
