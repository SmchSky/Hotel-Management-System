package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetReservationRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetReservationRecordListController {
    @Autowired
    private GetReservationRecordListService getReservationRecordListService;

    @PostMapping("/front_desk_staff/get_reservation_record_list/")
    public Map<String, Object> getList(@RequestParam Map<String, String> data) {
        return getReservationRecordListService.getList(data);
    }
}
