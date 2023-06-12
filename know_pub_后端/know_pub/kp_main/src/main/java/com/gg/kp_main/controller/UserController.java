package com.gg.kp_main.controller;

import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.vo.*;
import com.gg.kp_common.feign.OssFeignClient;
import com.gg.kp_common.service.UserService;
import com.gg.kp_common.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody RegisterUser user) {
        return userService.login(user);
    }

    @DeleteMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public Result<?> logout() {
        return userService.logout();
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterUser user) {
        return userService.register(user);
    }

    @GetMapping("/info-detail")
    public Result<UserInfoDetailVo> infoDetail(@RequestParam String userId) {
        return userService.infoDetail(userId);
    }

    @GetMapping("/info-short")
    public Result<UserInfoShortVo> infoShort(@RequestParam String userId) {
        return userService.infoShort(userId);
    }

    @GetMapping("/post-card")
    public Result<UserPostCardVo> postCard(@RequestParam String userId) {
        return userService.postCard(userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/follow")
    public Result<Integer> follow(@RequestParam String userId) {
        return userService.follow(userId);
    }

    @GetMapping("recommended-user")
    public Result<List<UserInfoShortVo>> getRecommendedUser() {
        return userService.getRecommendedUser();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/update")
    public Result<?> update(@RequestBody UpdateUser user) {
        return userService.updateUserInfo(user);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/update-avatar")
    public Result<Integer> updateAvatar(@RequestBody HashMap<String,Object> param){
        return userService.updateAvatar(param.get("dir").toString());

    }




}
