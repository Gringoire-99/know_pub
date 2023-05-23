package com.gg.kp_main;

import com.gg.kp_common.dao.sys.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class KpMainApplicationTests {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void test1() {
        System.out.println(new BCryptPasswordEncoder().encode("123456789"));
    }
}
