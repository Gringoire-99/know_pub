package com.gg.kp_admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

    import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 菜单表
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 
         */
                @TableId
            private String id;
            /**
         * 菜单名
         */
            private String menuName;
            /**
         * 路由地址
         */
            private String path;
            /**
         * 组件路径
         */
            private String component;
            /**
         * 菜单状态（0显示 1隐藏）
         */
            private String visible;
            /**
         * 菜单状态（0正常 1停用）
         */
            private String status;
            /**
         * 权限标识
         */
            private String perms;
            /**
         * 菜单图标
         */
            private String icon;
            /**
         * 
         */
            private Long createBy;
            /**
         * 
         */
            private Date createTime;
            /**
         * 
         */
            private Long updateBy;
            /**
         * 
         */
            private Date updateTime;
            /**
         * 是否删除（0未删除 1已删除）
         */
            private Integer delFlag;
            /**
         * 备注
         */
            private String remark;
    
}
