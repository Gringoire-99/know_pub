package com.gg.kp_common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

public class PageUtils<T> {
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_SIZE = "pageSize";
    public static final String ORDER_FIELD = "orderField";
    public static final String ORDER = "order";
    public static final String ASC = "asc";
    public static final String PAGE = "page";
    public static final String TOTAL = "total";


    //    根据请求参数构造page对象，通过这个page对象分页查询数据
    public IPage<T> getPage(Map<String, Object> params) {
        long currentPage = 1;
        long pageSize = 10;

        if (params.get(CURRENT_PAGE) != null) {
            currentPage = Long.parseLong( params.get(CURRENT_PAGE).toString());
        }
        if (params.get(PAGE_SIZE) != null) {
            pageSize = Long.parseLong( params.get(PAGE_SIZE).toString());
        }

        Page<T> page = new Page<>(currentPage, pageSize);

        if (params.get(ORDER_FIELD) != null) {
            String orderField = (String) params.get(ORDER_FIELD);
            String order = (String) params.get(ORDER);
            if (ASC.equals(order)) {
                page.addOrder(OrderItem.asc(orderField));
            } else {
                page.addOrder(OrderItem.desc(orderField));
            }
        }
        params.put(PAGE, page);
        return page;

    }
}
