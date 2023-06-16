package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @TableId
    private String id;
    private String question;
    private String content;
    private String cover;
    private String images;
    private int likeCount;
    private String userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int status;
    private int commentCount;
    private int answerCount;
    private int collectionCount;
    private int viewCount;
    private String tagNames;
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
    private Integer isAnonymous;
}
