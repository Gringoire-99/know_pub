package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Tag;
import com.gg.kp_common.entity.vo.TagVo;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

import java.util.Map;

public interface TagService extends IService<Tag> {
    Result<Page<TagVo>> getTags(PageParams params);
}
