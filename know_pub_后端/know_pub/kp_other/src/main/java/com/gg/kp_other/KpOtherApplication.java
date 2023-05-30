package com.gg.kp_other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients({"com.gg.kp_common.feign"})
@SpringBootApplication(scanBasePackages={"com.gg.kp_common","com.gg.kp_other"})
public class KpOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpOtherApplication.class, args);
    }

}
