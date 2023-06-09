package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo {
    private String id;
    private String question;
    private String content;
    private String cover;
    private String images;
    private int likeCount;
    private String userId;
    private String name;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private int commentCount;
    private int answerCount;
    private int collectionCount;
    private int viewCount;
    private String tagNames;
    private Integer isAnonymous;
}
