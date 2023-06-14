package com.gg.kp_common.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAction {
    private int liked;
    private int collected;
    private int disliked;
    private int replied;
    private String userId;
    private LocalDateTime createTime;
    private String targetId;
    private LocalDateTime updateTime;
}
