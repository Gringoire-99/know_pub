package com.gg.kp_main.controller;

import com.gg.kp_common.entity.po.User;
import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.entity.vo.UserInfoVo;
import com.gg.kp_common.entity.vo.UserVo;
import com.gg.kp_common.service.IUserService;
import com.gg.kp_common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * user: 必须包含username和password，其中username是email
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }
    @DeleteMapping("/logout")
    public Result<?> logout(){
        return userService.logout();
    }
    @PreAuthorize("hasAuthority('user:info-detail')")
    @GetMapping("/info-detail")
    public Result<UserVo> infoDetail(){
        UserDetail details =(UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.infoDetail(details.getUser().getId());
    }
 
}
