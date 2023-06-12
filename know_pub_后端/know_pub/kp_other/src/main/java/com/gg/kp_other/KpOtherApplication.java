package com.gg.kp_other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOpenApi
@EnableDiscoveryClient
@EnableFeignClients({"com.gg.kp_common.feign"})
@SpringBootApplication(scanBasePackages={"com.gg.kp_common","com.gg.kp_other"})
public class KpOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpOtherApplication.class, args);
    }

}
