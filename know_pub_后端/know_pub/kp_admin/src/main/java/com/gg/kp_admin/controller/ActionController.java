package com.gg.kp_admin.controller;

import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.R;
import com.gg.kp_admin.entity.ActionEntity;
import com.gg.kp_admin.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@RestController
@RequestMapping("kp_admin/action")
public class ActionController {
    @Autowired
    private ActionService actionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = actionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        ActionEntity action = actionService.getById(id);

        return R.ok().put("action", action);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ActionEntity action) {
        actionService.save(action);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ActionEntity action) {
        actionService.updateById(action);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        actionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
