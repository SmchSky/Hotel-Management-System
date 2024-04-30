package com.hotelmanagementsystem.backend.controller.online_user.reservation;

import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.ReserveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReserveRoomController {
    @Autowired
    private ReserveRoomService reserveRoomService;

    @PostMapping("/online_user/reserve/")  //要修改数据库，所以用PostMapping
    public Map<String, String> reserve(@RequestParam Map<String, String> data) {
        return reserveRoomService.reserve(data);
    }
}
