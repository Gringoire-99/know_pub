package com.gg.kp_common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.kp_common.config.exception.SystemException;

public class PageUtils<T> {
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_SIZE = "pageSize";
    public static final String ORDER_By = "orderBy";
    public static final String ORDER = "order";
    public static final String ASC = "asc";
    public static final String PAGE = "page";
    public static final String TOTAL = "total";
    public static final String ROWS = "rows";
    public static final String CREATE_TIME = "create_time";
    public static final String KEYWORD = "keyword";


    //    根据请求参数构造page对象，通过这个page对象分页查询数据
    public IPage<T> getPage(PageParams params) {
        long currentPage = 1;
        long pageSize = 10;

        if (params.getCurrentPage() != null) {
            currentPage = params.getCurrentPage();
        }
        if (params.getPageSize() != null) {
            pageSize = params.getPageSize();
            if (pageSize > 1000) {
                throw new SystemException("pageSize不能大于1000");
            }
        }

        Page<T> page = new Page<>(currentPage, pageSize);

        if (params.getOrderBy() != null) {
            String orderField = params.getOrderBy();
            String order = params.getOrder();
            if (ASC.equals(order)) {
                page.addOrder(OrderItem.asc(orderField));
            } else {
                page.addOrder(OrderItem.desc(orderField));
            }
        }
        return page;

    }

}
