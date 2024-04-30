package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OnlineUserInfoController {
    @Autowired
    private OnlineUserInfoService infoService;

    @GetMapping("/online_user/account/info/")
    public Map<String, String> getInfo() {
        //获取当前已登录的用户信息
        return infoService.getInfo();
    }
}
