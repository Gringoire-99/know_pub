package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {// 标签名
    private String tagId;
    private String name;
    // 标签描述
    private String description;

    // 浏览数，默认值为 0
    private int viewCount;

    // 问题数，默认值为 0
    private int questionCount;

    // 讨论数，默认值为 0
    private int discussCount;

    // 收藏数，默认值为 0
    private int collectionCount;

}
