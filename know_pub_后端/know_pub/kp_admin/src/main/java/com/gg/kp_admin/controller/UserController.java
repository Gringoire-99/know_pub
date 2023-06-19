package com.gg.kp_admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gg.kp_admin.entity.UserEntity;
import com.gg.kp_admin.service.UserService;
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
@RequestMapping("kp_admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
     @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
     @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
            UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
   @PostMapping("/save")
    public R save(@RequestBody UserEntity user) {
            userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
   @PostMapping("/update")
    public R update(@RequestBody UserEntity user) {
            userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
   @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
            userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
