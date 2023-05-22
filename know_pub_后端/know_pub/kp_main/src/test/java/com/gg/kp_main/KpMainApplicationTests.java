package com.gg.kp_main;

import com.gg.kp_common.dao.sys.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class KpMainApplicationTests {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("123"));
    }

}
