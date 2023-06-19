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
@TableName("action")
public class ActionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * id
         */
                @TableId
            private String id;
            /**
         * 0未点赞，1点赞
         */
            private Integer liked;
            /**
         * 0未收藏，1收藏
         */
            private Integer collected;
            /**
         * 0未点踩，1点踩
         */
            private Integer disliked;
            /**
         * 0未评论，1评论
         */
            private Integer replied;
            /**
         * 用户id
         */
            private String userId;
            /**
         * 创建时间
         */
            private Date createTime;
            /**
         * 目标id(postId)
         */
            private String targetId;
            /**
         * 更新时间
         */
            private Date updateTime;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
    
}
