package com.hotelmanagementsystem.backend.controller.administrator.account;

import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class StaffUserLoginController {
    
    private final StaffUserLoginService loginService;
    
    @Autowired
    public StaffUserLoginController(StaffUserLoginService loginService) {
        this.loginService = loginService;
    }
    
    @PostMapping("/staff_user/login")
    public Map<String, String> login(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        String duty = data.get("duty");
        return loginService.login(username, password, duty);
    }
}
