package com.gg.kp_common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {
    private Long currentPage;
    private Long pageSize;
    private String orderBy;
    private String order;
    private String keyword;
}
