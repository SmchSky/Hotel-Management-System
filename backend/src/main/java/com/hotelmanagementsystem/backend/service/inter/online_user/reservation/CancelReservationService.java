package com.hotelmanagementsystem.backend.service.inter.online_user.reservation;

import java.util.Map;

public interface CancelReservationService {
    Map<String, String> cancel(Map<String,String> data);
}
