package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.AddRoomService;
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
public class AddRoomController {
    
    private final AddRoomService addRoomService;
    
    @Autowired
    public AddRoomController(AddRoomService addRoomService) {
        this.addRoomService = addRoomService;
    }

    @PostMapping("/front_desk_staff/add_room")
    public Map<String, String> addRoom(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("酒店前台工作人员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return addRoomService.addRoom(data);
    }
}
