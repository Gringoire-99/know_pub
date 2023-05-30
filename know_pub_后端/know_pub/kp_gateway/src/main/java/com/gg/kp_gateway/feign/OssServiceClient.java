package com.gg.kp_gateway.feign;

import com.gg.kp_common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kp-other")
public interface OssServiceClient {
    @PostMapping("/oss/upload-avatar")
    public Result<?> uploadAvatar(@RequestParam("dir") String dir, @RequestBody String content);
    @GetMapping("/oss/test")
    public Integer test();
}
