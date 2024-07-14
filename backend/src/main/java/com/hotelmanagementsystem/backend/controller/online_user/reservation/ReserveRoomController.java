package com.hotelmanagementsystem.backend.controller.online_user.reservation;

import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.ReserveRoomService;
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
public class ReserveRoomController {
    
    private final ReserveRoomService reserveRoomService;
    
    @Autowired
    public ReserveRoomController(ReserveRoomService reserveRoomService) {
        this.reserveRoomService = reserveRoomService;
    }

    @PostMapping("/online_user/reserve")
    public Map<String, String> reserve(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return reserveRoomService.reserve(data);
    }
}
