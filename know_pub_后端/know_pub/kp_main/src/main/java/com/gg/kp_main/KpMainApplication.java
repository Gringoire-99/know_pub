package com.gg.kp_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@ComponentScan(basePackages = {"com.gg.kp_common", "com.gg.kp_main","com.gg.kp_feign"})
public class KpMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpMainApplication.class, args);
    }

}
