package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private OnlineUserRegisterService onlineUserRegisterService;

    @PostMapping("/online_user/account/register/")  //要修改数据库，所以用PostMapping
    public Map<String, String> register(@RequestParam Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");
        String confirmed_password = data.get("confirmed_password");
        String phone = data.get("phone");
        return onlineUserRegisterService.register(username, password, confirmed_password, phone);
    }
}
