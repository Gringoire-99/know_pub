package com.gg.kp_admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

    import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@Data
@TableName("comment")
public class CommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 评论id
         */
                @TableId
            private String id;
            /**
         * 评论内容
         */
            private String content;
            /**
         * 评论时间
         */
            private Date createTime;
            /**
         * 点赞数
         */
            private Integer likeCount;
            /**
         * 状态,0:正常,1:禁用
         */
            private Integer status;
            /**
         * 评论的文章id
         */
            private String postId;
            /**
         * 这条评论发布者的id
         */
            private String userId;
            /**
         * 根评论的id,如果自己是根评论，此条为自己的id
         */
            private String rootCommentId;
            /**
         * 被回复者的名字
         */
            private String replyToUserName;
            /**
         * 被回复者的id
         */
            private String replyToUserId;
            /**
         * 被回复的评论id
         */
            private String replyToCommentId;
            /**
         * 是否是根评论,1:是,0:不是
         */
            private Integer isRootComment;
            /**
         * 子评论数,当数量小于等于4时查出所有子评论,大于4时不查
         */
            private Integer childCount;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
    
}
