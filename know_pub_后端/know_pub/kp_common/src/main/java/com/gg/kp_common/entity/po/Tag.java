package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @TableId
    private String tagId;
    // 标签名
    private String name;

    // 标签描述
    private String description;

    // 创建时间，默认值为当前时间
    private LocalDateTime createTime;

    // 状态，0:正常, 1:禁用，默认值为 0
    private int status;

    // 浏览数，默认值为 0
    private int viewCount;

    // 问题数，默认值为 0
    private int questionCount;

    // 讨论数，默认值为 0
    private int discussCount;

    // 收藏数，默认值为 0
    private int collectionCount;

    // 删除标志,0:未删除,1:已删除，默认值为 0
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
}
