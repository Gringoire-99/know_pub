package com.gg.kp_common.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String token;
    private UserInfoDetailVo userInfoDetailVo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
}
