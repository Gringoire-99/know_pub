package com.gg.kp_gateway;

import com.gg.kp_gateway.feign.OssServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients
class KpGatewayApplicationTests {
    @Autowired
    OssServiceClient ossServiceClient;

    @Test
    void contextLoads() {
        System.out.println(ossServiceClient.policy());
    }

}
