package com.gg.kp_common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    @TableId
    private String id;
    private int liked;
    private int collected;
    private int disliked;
    private int replied;
    private String userId;
    private LocalDateTime createTime;
    private String targetId;
    private LocalDateTime updateTime;
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
}
