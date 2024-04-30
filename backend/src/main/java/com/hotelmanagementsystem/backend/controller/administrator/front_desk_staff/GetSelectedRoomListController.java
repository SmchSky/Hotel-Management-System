package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetSelectedRoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetSelectedRoomListController {
    @Autowired
    private GetSelectedRoomListService getSelectedRoomListService;


    //由于不需要修改数据库，故使用GetMapping
    @GetMapping("/front_desk_staff/get_selected_room_list/")
    public Map<String,Object> getRoomList(@RequestParam Map<String,String> data) {
        return getSelectedRoomListService.getRoomList(data);
    }
}
