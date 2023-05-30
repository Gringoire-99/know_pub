package com.gg.kp_other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KpOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpOtherApplication.class, args);
    }

}
