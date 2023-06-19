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
@TableName("collection")
public class CollectionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * id
         */
                @TableId
            private String id;
            /**
         * 用户id
         */
            private String userId;
            /**
         * 标题
         */
            private String title;
            /**
         * 描述
         */
            private String description;
            /**
         * 收藏数
         */
            private Integer collectCount;
            /**
         * 关注数
         */
            private Integer followCount;
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
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
            /**
         * 是否私有,0:公开,1:私有
         */
            private Integer isPrivate;
    
}
