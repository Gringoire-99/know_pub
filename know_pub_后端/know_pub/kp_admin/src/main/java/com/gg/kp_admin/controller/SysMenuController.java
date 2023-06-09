package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.SysMenuEntity;
import com.gg.kp_admin.service.SysMenuService;
import com.gg.kp_admin.common.utils.PageUtils;
import com.gg.kp_admin.common.utils.R;


/**
 * 菜单表
 *
 * @author gg
 * @email gg@outlook.com
 * @date 2023-06-19 01:17:33
 */
@RestController
@RequestMapping("kp_admin/sysmenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
            SysMenuEntity sysMenu = sysMenuService.getById(id);

        return R.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SysMenuEntity sysMenu) {
            sysMenuService.save(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SysMenuEntity sysMenu) {
            sysMenuService.updateById(sysMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
            sysMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
