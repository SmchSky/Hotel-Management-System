package com.hotelmanagementsystem.backend.controller.administrator.purchase_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.purchase_staff.RegisterStuffService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class RegisterStuffController {
    
    private final RegisterStuffService registerStuffService;
    
    @Autowired
    public RegisterStuffController(RegisterStuffService registerStuffService) {
        this.registerStuffService = registerStuffService;
    }

    @PostMapping("/purchase_staff/register_stuff")
    public Map<String, String> registerStuff(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("采购人员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return registerStuffService.registerStuff(data);
    }
}
