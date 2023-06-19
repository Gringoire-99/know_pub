package com.gg.kp_admin.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.Query;

import com.gg.kp_admin.dao.CollectionItemDao;
import com.gg.kp_admin.entity.CollectionItemEntity;
import com.gg.kp_admin.service.CollectionItemService;


@Service("collectionItemService")
public class CollectionItemServiceImpl extends ServiceImpl<CollectionItemDao, CollectionItemEntity> implements CollectionItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectionItemEntity> page = this.page(
                new Query<CollectionItemEntity>().getPage(params),
                new QueryWrapper<CollectionItemEntity>()
        );

        return new PageUtils(page);
    }

}