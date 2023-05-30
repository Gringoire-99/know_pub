package com.gg.kp_other;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
class KpOtherApplicationTests {
    @Value("${spring.cloud.alicloud.bucket}")
    public String bucket;
    @Value("${spring.cloud.alicloud.access-key}")
    public String accessId;
    @Value("${spring.cloud.alicloud.oss.endpoint}")
    public String endpoint;

    @Test
    void contextLoads() {

    }

}
