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
@TableName("tag")
public class TagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * id
         */
                @TableId
            private String tagId;
            /**
         * 标签名
         */
            private String name;
            /**
         * 标签描述
         */
            private String description;
            /**
         * 创建时间
         */
            private Date createTime;
            /**
         * 状态,0:正常,1:禁用
         */
            private Integer status;
            /**
         * 浏览数
         */
            private Integer viewCount;
            /**
         * 问题数
         */
            private Integer questionCount;
            /**
         * 讨论数
         */
            private Integer discussCount;
            /**
         * 收藏数
         */
            private Integer collectionCount;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
    
}
