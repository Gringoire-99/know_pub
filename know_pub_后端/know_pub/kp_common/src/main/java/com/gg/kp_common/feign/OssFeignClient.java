package com.gg.kp_common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
@Component
@FeignClient("kp-other")
public interface OssFeignClient {
    @GetMapping("/oss/test")
    Integer test();
}
