package com.hotelmanagementsystem.backend.controller.online_user.reservation;

import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.GetReservationRecordsService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetReservationRecordsController {
    
    private final GetReservationRecordsService getReservationRecordsService;
    
    @Autowired
    public GetReservationRecordsController(GetReservationRecordsService getReservationRecordsService) {
        this.getReservationRecordsService = getReservationRecordsService;
    }
    
    @GetMapping("/online_user/get_reservation_records")
    public List<ReservationRecord> getRecordList() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("线上用户")) {
            return null;
        }
        return getReservationRecordsService.getRecordList();
    }
}
