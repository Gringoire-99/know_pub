package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("know_pub.user")
public class User implements Serializable {
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @TableId(value = "id", type = IdType.AUTO)
    // 用户id
    private String id;
    /**
     * 用来校验身份的字段，可以是email,也可以是phone
     */
    @TableField(exist = false)
    private String username;
    // 邮箱
    private String email;

    // 手机号
    private String phone;

    // 用户名
    private String name;

    // 密码
    private String password;

    // 头像，默认值为 'http://dummyimage.com/100x100'
    private String avatar;

    // 描述，默认值为 '这个人很懒，什么都没有留下'
    private String description;

    // 创建时间，默认值为当前时间
    private Date createTime;

    // 发表回答数，默认值为 0
    private int postCount;

    // 评论数，默认值为 0
    private int commentCount;

    // 点赞数，默认值为 0
    private int likeCount;

    // 关注数，默认值为 0
    private int followCount;

    // 粉丝数，默认值为 0
    private int followerCount;

    // 提问数，默认值为 0
    private int questionCount;

    // 回答数，默认值为 0
    private int answerCount;

    // 收藏数，默认值为 0
    private int collectionCount;

    // 视频数，默认值为 0
    private int videoCount;

    // 文章数，默认值为 0
    private int articleCount;

    // 状态，0:正常, 1:禁用，默认值为 0
    private int status;

    // koin货币数，默认值为 0
    private int koinCount;

    // 经验值，默认值为 0
    private int experience;

    // 行业，默认值为空字符串
    private String industry;

    // 公司，默认值为空字符串
    private String company;

    // 职位，默认值为空字符串
    private String job;

    // 学校，默认值为空字符串
    private String school;

    // 地区，默认值为空字符串
    private String location;

    // 性别，默认值为空字符串
    private String gender;

    // 简单介绍，默认值为空字符串
    private String resume;

    // 个人认证，默认值为 0
    private String auth;

    // 背景图，默认值为 'http://dummyimage.com/2000x1000'
    private String background;

    private Integer del_flag;
}
