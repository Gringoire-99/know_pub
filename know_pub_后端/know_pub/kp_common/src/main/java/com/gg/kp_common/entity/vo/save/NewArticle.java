package com.gg.kp_common.entity.vo.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewArticle {


    @Schema(description = "标题", required = true)
    private String title;

    @Schema(description = "内容(富文本)", required = true)
    private String content;

    @Schema(description = "标签名，使用+分隔")
    private String tags;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "图片，使用+分隔")
    private String images;


    @Schema(description = "用户id", required = true)
    private String userId;


}
