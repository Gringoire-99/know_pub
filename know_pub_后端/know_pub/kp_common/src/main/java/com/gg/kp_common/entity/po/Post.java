package com.gg.kp_common.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

    private String id;

    private String question;

    private String questionId;

    private String content;

    private String cover = "";

    private String images = "";

    private Integer likeCount = 0;

    private Integer dislikeCount = 0;

    private String authorId;

    private Date publishTime;

    private Date updateTime;

    private Integer status = 0;

    private Integer commentCount = 0;

    private Integer delFlag = 0;

}
