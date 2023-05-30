package com.gg.kp_main;

import com.gg.kp_common.dao.sys.MenuMapper;
import com.gg.kp_common.feign.OssFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class KpMainApplicationTests {
   @Autowired
    OssFeignClient ossFeignClient;

    @Test
    void test1() {
        ossFeignClient.test();
    }
}
