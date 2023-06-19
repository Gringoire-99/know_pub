package com.gg.kp_common.entity.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    @Schema(description = "总数")
    Long total;
    @Schema(description = "查询行数")
    Long rows;
    @Schema(description = "分页数据")
    List<T> page;
    @Schema(description = "额外数据")
    HashMap<String, Object> extra = new HashMap<>();

    public Page(Long total, Long rows, List<T> page) {
        this.total = total;
        this.rows = rows;
        this.page = page;
    }

}
