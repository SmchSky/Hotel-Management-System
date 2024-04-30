package com.hotelmanagementsystem.backend.controller.administrator.account;

import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StaffUserInfoUpdateController {
    @Autowired
    private StaffUserInfoUpdateService staffUserInfoUpdateService;

    @PostMapping("/staff_user/update_info/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return staffUserInfoUpdateService.update(data);
    }

}
