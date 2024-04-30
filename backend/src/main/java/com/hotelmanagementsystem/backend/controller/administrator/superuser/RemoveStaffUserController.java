package com.hotelmanagementsystem.backend.controller.administrator.superuser;

import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.RemoveStaffUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveStaffUserController {
    @Autowired
    private RemoveStaffUserService removeService;

    @PostMapping("/superuser/remove_staff_user/")  //要修改数据库，所以用PostMapping
    public Map<String, String> removeStaffUser(@RequestParam Map<String, String> data) {
        return removeService.removeStaffUser(data);
    }
}
