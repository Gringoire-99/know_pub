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
@TableName("post")
public class PostEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 博文id
         */
                @TableId
            private String id;
            /**
         * 问题/标题
         */
            private String question;
            /**
         * 问题id
         */
            private String questionId;
            /**
         * 内容(富文本)
         */
            private String content;
            /**
         * 封面
         */
            private String cover;
            /**
         * 图片，使用逗号分隔
         */
            private String images;
            /**
         * 点赞数
         */
            private Integer likeCount;
            /**
         * 踩数
         */
            private Integer dislikeCount;
            /**
         * 作者id,需要根据id去user表中查找基础信息
         */
            private String userId;
            /**
         * 发布时间
         */
            private Date createTime;
            /**
         * 更新时间
         */
            private Date updateTime;
            /**
         * 状态,0:正常,1:禁用
         */
            private Integer status;
            /**
         * 评论数
         */
            private Integer commentCount;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
    
}
