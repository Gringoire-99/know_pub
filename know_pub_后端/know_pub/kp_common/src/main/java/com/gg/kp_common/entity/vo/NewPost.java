package com.gg.kp_common.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPost {
    @Schema(required = true)
    private String question;
    private String questionId;
    @Schema(required = true)
    private String content;

    private String cover = "";

    private String images = "";
}
