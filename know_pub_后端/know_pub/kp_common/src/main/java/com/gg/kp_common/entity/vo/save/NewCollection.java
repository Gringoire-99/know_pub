package com.gg.kp_common.entity.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCollection {

    @Schema(description = "标题", required = true)
    private String title;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "是否私有, 0:公开, 1:私有", defaultValue = "0")
    private int isPrivate;
}
