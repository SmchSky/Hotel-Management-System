package com.hotelmanagementsystem.backend.controller.online_user.reservation;

import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.GetUserReservationRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUserReservationRecordListController {
    @Autowired
    private GetUserReservationRecordListService getUserReservationRecordListService;

    @GetMapping("/online_user/get_reservation_records/")
    public List<ReservationRecord> getRecordList() {
        return getUserReservationRecordListService.getRecordList();
    }
}
