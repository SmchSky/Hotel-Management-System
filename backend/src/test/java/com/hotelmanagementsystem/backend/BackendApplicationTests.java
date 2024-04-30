package com.hotelmanagementsystem.backend;

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
    void contextLoads() {

        Date A = Date.valueOf("2023-06-07");
        Date B = Date.valueOf("2023-06-08");
        Date C = Date.valueOf("2023-06-07");

        System.out.println(B.after(A));
        System.out.println(C.equals(A));

    }

}
