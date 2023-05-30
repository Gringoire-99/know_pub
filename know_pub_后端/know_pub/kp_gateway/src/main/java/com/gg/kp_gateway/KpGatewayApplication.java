package com.gg.kp_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpGatewayApplication.class, args);
    }

}
