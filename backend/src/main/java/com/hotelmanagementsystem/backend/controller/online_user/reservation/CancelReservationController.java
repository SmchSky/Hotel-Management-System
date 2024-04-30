package com.hotelmanagementsystem.backend.controller.online_user.reservation;

import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.CancelReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CancelReservationController {
    @Autowired
    private CancelReservationService cancelReservationService;

    @PostMapping("/online_user/cancel_reservation/")  //要修改数据库，所以用PostMapping
    public Map<String, String> cancel(@RequestParam Map<String, String> data) {
        return cancelReservationService.cancel(data);
    }
}
