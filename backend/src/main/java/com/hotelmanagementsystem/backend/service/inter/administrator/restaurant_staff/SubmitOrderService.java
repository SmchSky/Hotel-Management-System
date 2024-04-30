package com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff;


import java.util.Map;

public interface SubmitOrderService {
    Map<String, String> submitOrder(Map<String, String> data);
}
