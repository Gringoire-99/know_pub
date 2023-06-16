package com.gg.kp_common.entity.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCollectionItem {

    @Schema(description = "收藏夹id", required = true)
    private String collectionId;

    @Schema(description = "目标id", required = true)
    private String targetId;

    @Schema(description = "枚举类型-回答 问题 文章 话题 收藏夹", required = true)
    private String type;


}
