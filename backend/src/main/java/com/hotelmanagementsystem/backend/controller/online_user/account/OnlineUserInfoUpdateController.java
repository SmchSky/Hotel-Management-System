package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OnlineUserInfoUpdateController {

    @Autowired
    OnlineUserInfoUpdateService onlineUserInfoUpdateService;

    @PostMapping("/onlineuser_user/update_info/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return onlineUserInfoUpdateService.update(data);
    }
}
