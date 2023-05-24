package com.gg.kp_common.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String content;
    private String name;
    private String avatar;
    private LocalDateTime createTime;
    private int likeCount;
    private int status;
    private String postId;
    private String userId;
    private String parentId;
    private String replyToUserName;
    private String replyToUserId;
    private int isRootComment;
    private int childCount;
    private int delFlag;
}
