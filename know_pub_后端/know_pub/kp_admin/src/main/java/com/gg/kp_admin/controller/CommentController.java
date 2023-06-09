package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.CommentEntity;
import com.gg.kp_admin.service.CommentService;
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
@RequestMapping("kp_admin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
            CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CommentEntity comment) {
            commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CommentEntity comment) {
            commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
            commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
