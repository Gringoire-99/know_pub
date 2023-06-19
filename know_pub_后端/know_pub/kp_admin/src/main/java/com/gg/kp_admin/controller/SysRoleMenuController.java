package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.SysRoleMenuEntity;
import com.gg.kp_admin.service.SysRoleMenuService;
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
@RequestMapping("kp_admin/sysrolemenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
     @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
     @GetMapping("/info/{roleId}")
    public R info(@PathVariable("roleId") String roleId) {
            SysRoleMenuEntity sysRoleMenu = sysRoleMenuService.getById(roleId);

        return R.ok().put("sysRoleMenu", sysRoleMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysRoleMenuEntity sysRoleMenu) {
            sysRoleMenuService.save(sysRoleMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysRoleMenuEntity sysRoleMenu) {
            sysRoleMenuService.updateById(sysRoleMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] roleIds) {
            sysRoleMenuService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
