package com.gg.kp_common.feign;

import com.gg.kp_common.entity.vo.OssPolicy;
import com.gg.kp_common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@FeignClient("kp-other")
public interface OssFeignClient {

    @PostMapping("/upload-avatar")
    Result<Integer> uploadAvatar(@RequestBody Map<String,Object> param);
    @RequestMapping("/oss/policy")
    Result<OssPolicy> policy();
}
