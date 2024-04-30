package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.AddRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddRoomController {
    @Autowired
    private AddRoomService addRoomService;

    @PostMapping("/front_desk_staff/add_room/")  //要修改数据库，所以用PostMapping
    public Map<String, String> addRoom(@RequestParam Map<String, String> data) {
        return addRoomService.addRoom(data);
    }
}
