package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.po.Tag;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

import java.util.Map;

public interface TagService extends IService<Tag> {
    Result<Map<String, Object>> getTags(PageParams params);
}
