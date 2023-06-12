package com.gg.kp_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableFeignClients(basePackages = {"com.gg.kp_common", "com.gg.kp_main"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@ComponentScan(basePackages = {"com.gg.kp_common", "com.gg.kp_main"})
@EnableOpenApi
public class KpMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpMainApplication.class, args);
    }

}
