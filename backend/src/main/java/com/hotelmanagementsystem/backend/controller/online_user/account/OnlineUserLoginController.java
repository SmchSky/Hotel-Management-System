package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OnlineUserLoginController {
    @Autowired
    private OnlineUserLoginService loginService;

    @PostMapping("/online_user/account/login/")
    public Map<String, String> getToken(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        return loginService.getToken(username, password);
    }

}
