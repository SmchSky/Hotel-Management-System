package com.hotelmanagementsystem.backend.controller.administrator.account;

import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StaffUserLoginController {
    @Autowired
    private StaffUserLoginService loginService;

    @PostMapping("/staff_user/account/login/")
    public Map<String, String> getToken(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        return loginService.getToken(username, password);
    }
}
