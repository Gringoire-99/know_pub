package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.CommentLikeEntity;
import com.gg.kp_admin.service.CommentLikeService;
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
@RequestMapping("kp_admin/commentlike")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentLikeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{commentId}")
    public R info(@PathVariable("commentId") String commentId) {
            CommentLikeEntity commentLike = commentLikeService.getById(commentId);

        return R.ok().put("commentLike", commentLike);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommentLikeEntity commentLike) {
            commentLikeService.save(commentLike);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CommentLikeEntity commentLike) {
            commentLikeService.updateById(commentLike);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] commentIds) {
            commentLikeService.removeByIds(Arrays.asList(commentIds));

        return R.ok();
    }

}
