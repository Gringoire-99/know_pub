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
@TableName("collection_item")
public class CollectionItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * id
         */
                @TableId
            private String id;
            /**
         * 收藏夹id
         */
            private String collectionId;
            /**
         * 目标id
         */
            private String targetId;
            /**
         * 枚举类型-回答 问题 文章 话题 收藏夹
         */
            private String type;
            /**
         * 用户id
         */
            private String userId;
            /**
         * 创建时间
         */
            private Date createTime;
            /**
         * 更新时间
         */
            private Date updateTime;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
            /**
         * 状态,0:正常,1:禁用
         */
            private Integer status;
    
}
