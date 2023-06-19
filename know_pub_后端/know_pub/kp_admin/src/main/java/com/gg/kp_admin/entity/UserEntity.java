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
@TableName("user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 用户id
         */
                @TableId
            private String id;
            /**
         * 邮箱
         */
            private String email;
            /**
         * 手机号
         */
            private String phone;
            /**
         * 用户名
         */
            private String name;
            /**
         * 密码
         */
            private String password;
            /**
         * 头像
         */
            private String avatar;
            /**
         * 描述
         */
            private String description;
            /**
         * 创建时间
         */
            private Date createTime;
            /**
         * 发表回答数
         */
            private Integer postCount;
            /**
         * 评论数
         */
            private Integer commentCount;
            /**
         * 点赞数
         */
            private Integer likeCount;
            /**
         * 关注数
         */
            private Integer followCount;
            /**
         * 粉丝数
         */
            private Integer followerCount;
            /**
         * 提问数
         */
            private Integer questionCount;
            /**
         * 回答数
         */
            private Integer answerCount;
            /**
         * 收藏数
         */
            private Integer collectionCount;
            /**
         * 视频数
         */
            private Integer videoCount;
            /**
         * 文章数
         */
            private Integer articleCount;
            /**
         * 状态,0:正常,1:禁用
         */
            private Integer status;
            /**
         * koin货币数
         */
            private Integer koinCount;
            /**
         * 经验值
         */
            private Integer experience;
            /**
         * 行业
         */
            private String industry;
            /**
         * 公司
         */
            private String company;
            /**
         * 职位
         */
            private String job;
            /**
         * 学校
         */
            private String school;
            /**
         * 地区
         */
            private String location;
            /**
         * 性别
         */
            private String gender;
            /**
         * 简单介绍
         */
            private String resume;
            /**
         * 个人认证
         */
            private String auth;
            /**
         * 背景图
         */
            private String background;
            /**
         * 删除标志,0:未删除,1:已删除
         */
            private Integer delFlag;
            /**
         * 
         */
            private String realName;
    
}
