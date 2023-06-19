package com.gg.kp_admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.entity.CommentEntity;

import java.util.Map;

/**
 * 
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

