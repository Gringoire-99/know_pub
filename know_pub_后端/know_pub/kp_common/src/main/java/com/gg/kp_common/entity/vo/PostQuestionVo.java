package com.gg.kp_common.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionVo {
    @Schema(required = true)
    private String question;
    @Schema(required = true)
    private String content;
    private String cover;
    private String images;
    @Schema(required = true)
    private String tagNames;
    @Schema(required = true)
    private Integer isAnonymous;
}
