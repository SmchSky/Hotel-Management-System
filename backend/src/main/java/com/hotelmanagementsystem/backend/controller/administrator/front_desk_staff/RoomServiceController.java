package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RoomService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class RoomServiceController {
    
    private final RoomService roomService;
    
    @Autowired
    public RoomServiceController(RoomService roomService) {
        this.roomService = roomService;
    }
    
    @PostMapping("/front_desk_staff/add_room")
    public Map<String, String> addRoom(@RequestParam Map<String, String> data) {
        if (authenticateFailed()) {
            return Map.of("message", "无权限执行该操作！");
        }
        return roomService.addRoom(data);
    }
    
    @GetMapping("/front_desk_staff/get_all_room_list")
    public List<Room> getRoomList() {
        if (authenticateFailed()) {
            return null;
        }
        return roomService.getAllRoomList();
    }
    
    @GetMapping("/front_desk_staff/get_selected_room_list")
    public Map<String, Object> getRoomList(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("酒店前台工作人员") && !role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return roomService.getSelectedRoomList(data);
    }
    
    @PostMapping("/front_desk_staff/remove_room")
    public Map<String, String> removeRoom(@RequestParam Map<String, String> data) {
        if (authenticateFailed()) {
            return Map.of("message", "无权限执行该操作！");
        }
        return roomService.removeRoom(data.get("number"));
    }
    
    @PostMapping("/front_desk_staff/update_room")
    public Map<String, String> updateRoom(@RequestParam Map<String, String> data) {
        if (authenticateFailed()) {
            return Map.of("message", "无权限执行该操作！");
        }
        return roomService.updateRoom(data);
    }
    
    /**
     * 酒店前台工作人员身份验证
     * @return 是酒店前台工作人员返回true，否则返回false
     */
    private boolean authenticateFailed() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        return !role.equals("酒店前台工作人员");
    }
}
