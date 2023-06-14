package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootCommentVo {
    private String id;
    private String content;
    private String name;
    private String avatar;
    private int likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String postId;
    private String userId;
    private String rootCommentId;
    private String replyToUserName;
    private String replyToUserId;
    private String replyToCommentId;
    private int childCount;
    private List<CommentVo> childComments;
}
