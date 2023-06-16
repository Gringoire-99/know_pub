package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @TableId
    private String id;
    private String content;
    private LocalDateTime createTime;
    private int likeCount;
    private int status;
    private String postId;
    private String userId;
    private String rootCommentId;
    private String replyToUserName;
    private String replyToUserId;
    private String replyToCommentId;
    private int isRootComment;
    private int childCount;
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
}
