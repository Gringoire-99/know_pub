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
@TableName("article")
public class ArticleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * id
         */
                @TableId
            private String id;
            /**
         * 标题
         */
            private String title;
            /**
         * 内容(富文本)
         */
            private String content;
            /**
         * 封面
         */
            private String cover;
            /**
         * 图片，使用+分隔
         */
            private String images;
            /**
         * 点赞数
         */
            private Integer likeCount;
            /**
         * 用户id
         */
            private String userId;
            /**
         * 标签名，使用+分隔
         */
            private String tags;
            /**
         * 创建时间
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
         * 状态,0:已发布,1:未发布
         */
            private Integer isPublish;
            /**
         * 评论数
         */
            private Integer commentCount;
            /**
         * 浏览数
         */
            private Integer viewCount;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
    
}
