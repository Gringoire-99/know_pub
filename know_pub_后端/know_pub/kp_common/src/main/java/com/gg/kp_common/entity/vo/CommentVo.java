package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private String id;
    private String content;
    private String name;
    private String avatar;
    private int likeCount;
    private String postId;
    private String userId;
    private String parentId;
    private String replyToUserName;
    private String replyToUserId;
    private int isRootComment;
    private int childCount;
}
