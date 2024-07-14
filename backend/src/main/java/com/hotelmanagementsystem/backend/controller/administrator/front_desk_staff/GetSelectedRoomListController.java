package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetSelectedRoomListService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetSelectedRoomListController {
    
    private final GetSelectedRoomListService getSelectedRoomListService;
    
    @Autowired
    public GetSelectedRoomListController(GetSelectedRoomListService getSelectedRoomListService) {
        this.getSelectedRoomListService = getSelectedRoomListService;
    }

    @GetMapping("/front_desk_staff/get_selected_room_list")
    public Map<String,Object> getRoomList(@RequestParam Map<String,String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("酒店前台工作人员") && !role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return getSelectedRoomListService.getRoomList(data);
    }
}
