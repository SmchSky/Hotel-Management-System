package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.UpdateRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class UpdateRoomController {
    @Autowired
    private UpdateRoomService updateRoomService;

    @PostMapping("/front_desk_staff/update_room/")
    public Map<String, String> updateRoom(@RequestParam Map<String, String> data) {
        return updateRoomService.updateRoom(data);
    }
}
