package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostComment {
    private String content;
    private String name;
    private String avatar;
    private String postId;
    private String userId;
    private String rootCommentId;
    private String replyToUserName;
    private String replyToUserId;
    private int isRootComment;
}
