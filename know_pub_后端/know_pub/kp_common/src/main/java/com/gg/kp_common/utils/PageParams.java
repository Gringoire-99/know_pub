package com.gg.kp_common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {
    @Schema(description = "当前页码")
    private Long currentPage;
    @Schema(description = "页大小")
    private Long pageSize;
    @Schema(description = "排序")
    private String orderBy;
    @Schema(description = "排序字段")
    private String order;
    @Schema(description = "关键词")
    private String keyword;
}
