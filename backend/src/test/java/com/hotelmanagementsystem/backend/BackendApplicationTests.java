package com.hotelmanagementsystem.backend;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.OnlineUserMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@SpringBootTest
class BackendApplicationTests {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    void test1() {
        System.out.println(passwordEncoder.matches("psmy", "$2a$10$4Ua6sm/PKdyGd7JHcNF20.6TDSqlKi0U0k.99jXNQGyOHH5wh3JSm"));
    }
}
