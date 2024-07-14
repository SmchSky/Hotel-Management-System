package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class RegisterController {
    
    private final OnlineUserRegisterService onlineUserRegisterService;

    @Autowired
    public RegisterController(OnlineUserRegisterService onlineUserRegisterService) {
        this.onlineUserRegisterService = onlineUserRegisterService;
    }
    
    @PostMapping("/online_user/register")
    public Map<String, String> register(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        String confirmed_password = data.get("confirmed_password");
        String phone = data.get("phone");
        return onlineUserRegisterService.register(username, password, confirmed_password, phone);
    }
}
