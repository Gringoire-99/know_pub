package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.SysUserRoleEntity;
import com.gg.kp_admin.service.SysUserRoleService;
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
@RequestMapping("kp_admin/sysuserrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
     @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
     @GetMapping("/info/{userId}")
    public R info(@PathVariable("userId") String userId) {
            SysUserRoleEntity sysUserRole = sysUserRoleService.getById(userId);

        return R.ok().put("sysUserRole", sysUserRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysUserRoleEntity sysUserRole) {
            sysUserRoleService.save(sysUserRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysUserRoleEntity sysUserRole) {
            sysUserRoleService.updateById(sysUserRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] userIds) {
            sysUserRoleService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
