package com.gg.kp_common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.kp_common.config.exception.SystemException;
import com.gg.kp_common.dao.CollectionItemMapper;
import com.gg.kp_common.dao.CollectionMapper;
import com.gg.kp_common.entity.model.Page;
import com.gg.kp_common.entity.po.Action;
import com.gg.kp_common.entity.po.Collection;
import com.gg.kp_common.entity.po.CollectionItem;
import com.gg.kp_common.entity.vo.CollectionItemVo;
import com.gg.kp_common.entity.vo.CollectionVo;
import com.gg.kp_common.entity.vo.save.NewCollection;
import com.gg.kp_common.entity.vo.save.NewCollectionItem;
import com.gg.kp_common.service.ActionService;
import com.gg.kp_common.service.CollectionService;
import com.gg.kp_common.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private CollectionItemMapper collectionItemMapper;
    @Autowired
    private ActionService actionService;

    @Override
    public Result<Integer> newCollection(NewCollection newCollection) {
        Collection collection = new Collection();
        BeanUtils.copyProperties(newCollection, collection);
        collection.setUserId(SecurityUtils.getId());
        int result = this.baseMapper.insert(collection);
        return Result.ok(result);
    }

    @Override
    public Result<Page<CollectionVo>> getCollections(String userId, PageParams pageParams) {
        String self = SecurityUtils.getId();
        LambdaQueryWrapper<Collection> lqw = new LambdaQueryWrapper<>();
        if (!userId.equals(self)) {
            lqw.eq(Collection::getIsPrivate, EntityConstant.NOT_PRIVATE);
        }
        lqw.eq(Collection::getUserId, userId);
        IPage<Collection> page = this.baseMapper.selectPage(new PageUtils<Collection>().getPage(pageParams), lqw);
        List<CollectionVo> data = BeanCopyUtils.copyBeanList(page.getRecords(), CollectionVo.class);
        Page<CollectionVo> pageData = new Page<CollectionVo>(page.getTotal(), page.getSize(), data);
        return Result.ok(pageData);
    }

    @Override
    public Result<Integer> addCollectionItem(NewCollectionItem collectionItem) {
        String type = collectionItem.getType();
        if (!CollectionItem.POST.equals(type) && !CollectionItem.QUESTION.equals(type)
                && !CollectionItem.ARTICLE.equals(type) && !CollectionItem.TOPIC.equals(type)
                && !CollectionItem.COLLECTION.equals(type)
        ) {
            throw new SystemException("收藏类型错误");
        }

        String userId = SecurityUtils.getId();
        LambdaQueryWrapper<Collection> lqwC = new LambdaQueryWrapper<>();
        lqwC.eq(Collection::getUserId, userId);
        lqwC.eq(Collection::getId, collectionItem.getCollectionId());
        Collection collection = this.baseMapper.selectOne(lqwC);
        if (collection == null) {
            throw new SystemException("收藏夹不存在");
        }
        LambdaQueryWrapper<CollectionItem> lqwItem = new LambdaQueryWrapper<>();
        lqwItem.eq(CollectionItem::getUserId, userId)
                .eq(CollectionItem::getCollectionId, collectionItem.getCollectionId())
                .eq(CollectionItem::getTargetId, collectionItem.getTargetId());
        CollectionItem oldOne = collectionItemMapper.selectOne(lqwItem);
        int result;
        if (Objects.nonNull(oldOne)) {
            collectionItemMapper.deleteById(oldOne.getId());
            LambdaQueryWrapper<Action> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Action::getUserId, userId);
            lqw.eq(Action::getTargetId, oldOne.getTargetId());
            Action one = actionService.getOne(lqw);
            one.setCollected(EntityConstant.ACTION_OFF);
            actionService.updateById(one);
            result = 0;
        } else {
            CollectionItem collectionItem1 = new CollectionItem();
            BeanUtils.copyProperties(collectionItem, collectionItem1);
            collectionItem1.setUserId(userId);
            result = collectionItemMapper.insert(collectionItem1);
            LambdaQueryWrapper<Action> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Action::getUserId, userId);
            lqw.eq(Action::getTargetId, collectionItem1.getTargetId());
            Action one = actionService.getOne(lqw);
            if (Objects.isNull(one)) {
                one = new Action();
                one.setUserId(userId);
                one.setTargetId(collectionItem1.getTargetId());
                one.setCollected(EntityConstant.ACTION_ON);
                actionService.save(one);
            } else {
                one.setCollected(EntityConstant.ACTION_ON);
                actionService.updateById(one);
            }
            one.setCollected(EntityConstant.ACTION_ON);
            collection.setCollectCount(collection.getCollectCount() + 1);
            this.baseMapper.updateById(collection);
        }


        return Result.ok(result);
    }

    @Override
    public Result<Page<CollectionItemVo>> getCollectionItems(String collectionId, String type, PageParams pageParams) {
        LambdaQueryWrapper<CollectionItem> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CollectionItem::getCollectionId, collectionId);
        lqw.eq(CollectionItem::getType, type);
        IPage<CollectionItem> page = collectionItemMapper.selectPage(new PageUtils<CollectionItem>().getPage(pageParams), lqw);
        List<CollectionItemVo> data = BeanCopyUtils.copyBeanList(page.getRecords(), CollectionItemVo.class);
        Page<CollectionItemVo> pageData = new Page<CollectionItemVo>(page.getTotal(), page.getSize(), data);
        return Result.ok(pageData);
    }

    @Override
    public Result<Integer> deleteCollectionItem(String collectionItemId) {
        String userId = SecurityUtils.getId();
        LambdaQueryWrapper<CollectionItem> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CollectionItem::getId, collectionItemId);
        lqw.eq(CollectionItem::getUserId, userId);
        int result = collectionItemMapper.delete(lqw);
        if (result == 1) {
            Action action = new Action();
            action.setTargetId(collectionItemId);
            action.setUserId(userId);
            action.setCollected(EntityConstant.ACTION_OFF);
            LambdaQueryWrapper<Action> lqw1 = new LambdaQueryWrapper<>();
            lqw1.eq(Action::getUserId, userId);
            lqw1.eq(Action::getTargetId, collectionItemId);
            actionService.saveOrUpdate(action, lqw1);
        } else {
            throw new SystemException("删除失败");
        }
        return Result.ok(result);
    }

    @Override
    public Result<Integer> deleteCollection(String collectionId) {
        String userId = SecurityUtils.getId();
        LambdaQueryWrapper<Collection> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collection::getId, collectionId);
        lqw.eq(Collection::getUserId, userId);
        int result = this.baseMapper.delete(lqw);
        return Result.ok(result);
    }

    @Override
    public Result<Integer> updateCollection(NewCollection newCollection, String collectionId) {
        String userId = SecurityUtils.getId();
        LambdaQueryWrapper<Collection> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collection::getId, collectionId);
        lqw.eq(Collection::getUserId, userId);
        Collection collection = new Collection();
        BeanUtils.copyProperties(newCollection, collection);
        collection.setId(collectionId);
        int result = this.baseMapper.updateById(collection);
        return Result.ok(result);
    }

    @Override
    public Result<CollectionVo> getCollectionById(String collectionId) {
        Collection collection = this.baseMapper.selectById(collectionId);
        return Result.ok(BeanCopyUtils.copyBean(collection, CollectionVo.class));
    }

}
