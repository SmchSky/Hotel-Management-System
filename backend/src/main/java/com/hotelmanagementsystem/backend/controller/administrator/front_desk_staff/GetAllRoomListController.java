package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetAllRoomListController {
    
    private final GetAllRoomListService getAllRoomListService;
    
    @Autowired
    public GetAllRoomListController(GetAllRoomListService getAllRoomListService) {
        this.getAllRoomListService = getAllRoomListService;
    }

    @GetMapping("/front_desk_staff/get_all_room_list")
    public List<Room> getRoomList() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("酒店前台工作人员")) {
            return null;
        }
        return getAllRoomListService.getRoomList();
    }
}
