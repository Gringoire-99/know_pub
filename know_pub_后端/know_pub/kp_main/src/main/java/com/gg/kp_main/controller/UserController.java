package com.gg.kp_main.controller;

import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.RegisterUser;
import com.gg.kp_common.entity.vo.UserInfoVo;
import com.gg.kp_common.entity.vo.UserVo;
import com.gg.kp_common.service.UserService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * user: 必须包含username和password，其中username是email
     */
    @PostMapping("/login")
    public Result<UserInfoVo> login(@RequestBody User user){
        return userService.login(user);
    }
    @DeleteMapping("/logout")
    public Result<?> logout(){
        return userService.logout();
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterUser user){
        return userService.register(user);
    }
    @PreAuthorize("hasAuthority('user:info-detail')")
    @GetMapping("/info-detail")
    public Result<UserVo> infoDetail(@RequestParam String userId){
        return userService.infoDetail(userId);
    }

}
