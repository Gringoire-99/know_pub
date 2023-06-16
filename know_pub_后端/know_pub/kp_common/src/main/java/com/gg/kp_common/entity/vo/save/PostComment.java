package com.gg.kp_common.entity.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostComment {
    @Schema(required = true)
    private String id;
    @Schema(required = true)
    private String content;
    @Schema(required = true)
    private String postId;
    @Schema(required = true)
    private String rootCommentId;

    //    只有是根评论的评论才有这些属性
    private String replyToUserName;
    private String replyToUserId;
    private String replyToCommentId;

    @Schema(required = true)
    private int isRootComment;
}
