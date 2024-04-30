package com.hotelmanagementsystem.backend.controller.administrator.purchase_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.purchase_staff.RegisterStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterStuffController {
    @Autowired
    private RegisterStuffService registerStuffService;

    @PostMapping("/purchase_staff/register_stuff/")  //要修改数据库，所以用PostMapping
    public Map<String, String> registerStuff(@RequestParam Map<String, String> data) {
        return registerStuffService.registerStuff(data);
    }
}
