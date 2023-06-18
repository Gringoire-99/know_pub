package com.gg.kp_common.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    @Schema(required = true, description = "oss凭证")
    OssPolicy ossPolicy;
    @Schema(required = false, description = "额外数据")
    Map<String,Object> extraData = new HashMap<>();

}
