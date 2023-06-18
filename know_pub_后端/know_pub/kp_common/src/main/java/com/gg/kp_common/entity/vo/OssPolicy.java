package com.gg.kp_common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssPolicy {
    private String ossaccessKeyId;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;
}
