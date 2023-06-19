package com.gg.kp_admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

    import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 角色表
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 
         */
                @TableId
            private String id;
            /**
         * 
         */
            private String name;
            /**
         * 角色权限字符串
         */
            private String roleKey;
            /**
         * 角色状态（0正常 1停用）
         */
            private String status;
            /**
         * del_flag
         */
            private Integer delFlag;
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
         * 备注
         */
            private String remark;
    
}
