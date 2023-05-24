package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostCardVo {
    private String id;

    private String name;

    private String avatar;

    private String description;
    private Integer postCount;
    private Integer articleCount;
    private Integer followerCount;

    private Boolean isFollowed;
}
