package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.TagEntity;
import com.gg.kp_admin.service.TagService;
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
@RequestMapping("kp_admin/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
     @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
     @GetMapping("/info/{tagId}")
    public R info(@PathVariable("tagId") String tagId) {
            TagEntity tag = tagService.getById(tagId);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TagEntity tag) {
            tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody TagEntity tag) {
            tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] tagIds) {
            tagService.removeByIds(Arrays.asList(tagIds));

        return R.ok();
    }

}
