package com.gg.kp_common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Collection;
import com.gg.kp_common.entity.vo.CollectionItemVo;
import com.gg.kp_common.entity.vo.CollectionVo;
import com.gg.kp_common.entity.vo.save.NewCollection;
import com.gg.kp_common.entity.vo.save.NewCollectionItem;
import com.gg.kp_common.utils.PageParams;
import com.gg.kp_common.utils.Result;

public interface CollectionService extends IService<Collection> {
    Result<Integer> newCollection(NewCollection newCollection);

    Result<Page<CollectionVo>> getCollections(String userId, PageParams pageParams);

    Result<Integer> addCollectionItem(NewCollectionItem collectionItem);

    Result<Page<CollectionItemVo>> getCollectionItems(String collectionId, String type,PageParams pageParams);

    Result<Integer> deleteCollectionItem(String collectionItemId);

    Result<Integer> deleteCollection(String collectionId);

    Result<Integer> updateCollection(NewCollection newCollection,String collectionId);

    Result<CollectionVo> getCollectionById(String collectionId);
}
