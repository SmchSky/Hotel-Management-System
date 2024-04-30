package com.hotelmanagementsystem.backend.controller.administrator.account;

import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StaffUserInfoController {
    @Autowired
    private StaffUserInfoService infoService;

    @GetMapping("/staff_user/account/info/")
    public Map<String, String> getInfo() {
        //获取当前已登录的用户信息
        return infoService.getInfo();
    }
}
