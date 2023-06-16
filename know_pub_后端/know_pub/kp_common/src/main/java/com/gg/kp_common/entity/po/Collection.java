package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Collection {
    @Schema(description = "id", required = true)
    private String id;

    @Schema(description = "用户id", required = true)
    private String userId;

    @Schema(description = "标题", required = true)
    private String title;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "收藏数", defaultValue = "0")
    private int collectCount;

    @Schema(description = "关注数", defaultValue = "0")
    private int followCount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "状态, 0:正常, 1:禁用", defaultValue = "0")
    private int status;

    @Schema(description = "删除标志, 0:未删除, 1:已删除", defaultValue = "0")
    @TableLogic(value = "0", delval = "1")
    private int delFlag;

    @Schema(description = "是否私有, 0:公开, 1:私有", defaultValue = "0")
    private int isPrivate;
}
