package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllRoomListController {
    @Autowired
    private GetAllRoomListService getAllRoomListService;

    //由于不需要修改数据库，故使用GetMapping
    @GetMapping("/front_desk_staff/get_all_room_list/")
    public List<Room> getRoomList() {
        return getAllRoomListService.getRoomList();
    }
}
