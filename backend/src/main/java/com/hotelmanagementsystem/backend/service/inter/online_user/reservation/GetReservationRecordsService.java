package com.hotelmanagementsystem.backend.service.inter.online_user.reservation;

import com.hotelmanagementsystem.backend.pojo.ReservationRecord;

import java.util.List;

public interface GetReservationRecordsService {
    List<ReservationRecord> getRecordList();
}
