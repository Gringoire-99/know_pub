package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostVo {
    private String id;

    private String question;

    private String questionId;

    private String content;

    private String cover = "";

    private String images = "";

    private Integer likeCount = 0;

    private Integer dislikeCount = 0;

    private String authorId;

    private Integer commentCount = 0;
}
