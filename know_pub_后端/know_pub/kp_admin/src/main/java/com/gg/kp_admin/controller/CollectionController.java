package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.CollectionEntity;
import com.gg.kp_admin.service.CollectionService;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.R;


/**
 * 
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@RestController
@RequestMapping("kp_admin/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = collectionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
            CollectionEntity collection = collectionService.getById(id);

        return R.ok().put("collection", collection);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CollectionEntity collection) {
            collectionService.save(collection);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CollectionEntity collection) {
            collectionService.updateById(collection);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids) {
            collectionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
