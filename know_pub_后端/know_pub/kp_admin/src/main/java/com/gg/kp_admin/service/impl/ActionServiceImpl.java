package com.gg.kp_admin.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.Query;

import com.gg.kp_admin.dao.ActionDao;
import com.gg.kp_admin.entity.ActionEntity;
import com.gg.kp_admin.service.ActionService;


@Service("actionService")
public class ActionServiceImpl extends ServiceImpl<ActionDao, ActionEntity> implements ActionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActionEntity> page = this.page(
                new Query<ActionEntity>().getPage(params),
                new QueryWrapper<ActionEntity>()
        );

        return new PageUtils(page);
    }

}