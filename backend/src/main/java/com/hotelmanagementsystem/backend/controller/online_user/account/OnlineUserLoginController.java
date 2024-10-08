package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class OnlineUserLoginController {
    
    private final OnlineUserLoginService loginService;

    @Autowired
    public OnlineUserLoginController(OnlineUserLoginService loginService) {
        this.loginService = loginService;
    }
    
    @PostMapping("/online_user/login")
    public Map<String, String> login(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        return loginService.login(username, password);
    }
}
