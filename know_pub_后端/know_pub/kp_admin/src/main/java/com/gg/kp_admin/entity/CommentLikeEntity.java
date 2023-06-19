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
@TableName("comment_like")
public class CommentLikeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 被点赞评论id
         */
                @TableId
            private String commentId;
            /**
         * 点赞人id
         */
            private String userId;
    
}
