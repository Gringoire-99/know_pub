package com.gg.kp_admin.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.Query;

import com.gg.kp_admin.dao.CollectionDao;
import com.gg.kp_admin.entity.CollectionEntity;
import com.gg.kp_admin.service.CollectionService;


@Service("collectionService")
public class CollectionServiceImpl extends ServiceImpl<CollectionDao, CollectionEntity> implements CollectionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectionEntity> page = this.page(
                new Query<CollectionEntity>().getPage(params),
                new QueryWrapper<CollectionEntity>()
        );

        return new PageUtils(page);
    }

}