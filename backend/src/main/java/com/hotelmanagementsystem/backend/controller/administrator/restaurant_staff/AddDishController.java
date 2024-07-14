package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.AddDishService;
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
public class AddDishController {
    
    private final AddDishService addDishService;
    
    @Autowired
    public AddDishController(AddDishService addDishService) {
        this.addDishService = addDishService;
    }

    @PostMapping("/restaurant_staff/add_dish")
    public Map<String, String> addRoom(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("餐厅前台工作人员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return addDishService.addDish(data);
    }
}
