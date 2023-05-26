package com.gg.kp_common.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private String action;
    private String actionId;
    private String userId;
    private LocalDateTime createTime;
    private String targetId;
    private String content;
    public static String LIKE = "点赞";
    public static String DISLIKE = "踩";
    public static String COLLECTION = "收藏";
    public static String REPLY = "回复";

}
