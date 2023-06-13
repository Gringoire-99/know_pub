package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String userId;

    private Integer commentCount = 0;
    private int liked = 0;
    private int collected = 0;
    private int disliked = 0;
    private int replied = 0;
}
