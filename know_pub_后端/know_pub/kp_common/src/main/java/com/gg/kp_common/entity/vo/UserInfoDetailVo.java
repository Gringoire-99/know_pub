package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDetailVo {
    private String id;

    private String name;
    private String realName;

    private String avatar;

    private String description;
    private LocalDateTime createTime;


    private Integer commentCount;

    private Integer likeCount;

    private Integer postCount;
    private Integer articleCount;
    private Integer followerCount;

    private Integer followCount;

    private Integer questionCount;

    private Integer answerCount;

    private Integer collectionCount;

    private Integer videoCount;

    private Integer koinCount;

    private Integer experience;

    private String industry;

    private String company;

    private String job;

    private String school;

    private String location;

    private String gender;

    private String resume;

    private String auth;

    private String background;
}
