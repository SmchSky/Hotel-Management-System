package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RemoveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveRoomController {
    @Autowired
    private RemoveRoomService removeRoomService;

    @PostMapping("/front_desk_staff/remove_room/")
    public Map<String, String> removeRoom(@RequestParam Map<String, String> data) {
        return removeRoomService.removeRoom(data.get("number"));
    }
}
