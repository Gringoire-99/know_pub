package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String token;
    private UserInfoDetailVo userInfoDetailVo;
}
