package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItemVo {
    @Schema(description = "id", required = true)
    private String id;

    @Schema(description = "收藏夹id", required = true)
    private String collectionId;

    @Schema(description = "目标id", required = true)
    private String targetId;

    @Schema(description = "枚举类型-回答 问题 文章 话题 收藏夹", required = true)
    private String type;

    @Schema(description = "用户id", required = true)
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


}
