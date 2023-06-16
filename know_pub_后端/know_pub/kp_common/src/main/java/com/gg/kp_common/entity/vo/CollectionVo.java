package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionVo {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否私有, 0:公开, 1:私有", defaultValue = "0")
    private int isPrivate;
}
