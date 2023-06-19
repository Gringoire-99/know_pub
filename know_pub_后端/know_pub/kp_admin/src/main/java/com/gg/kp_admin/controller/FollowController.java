package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.FollowEntity;
import com.gg.kp_admin.service.FollowService;
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
@RequestMapping("kp_admin/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = followService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{followId}")
    public R info(@PathVariable("followId") String followId) {
            FollowEntity follow = followService.getById(followId);

        return R.ok().put("follow", follow);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody FollowEntity follow) {
            followService.save(follow);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody FollowEntity follow) {
            followService.updateById(follow);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] followIds) {
            followService.removeByIds(Arrays.asList(followIds));

        return R.ok();
    }

}
