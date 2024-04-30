package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.SubmitOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SubmitOrderController {
    @Autowired
    private SubmitOrderService submitOrderService;
    @PostMapping("restaurant_staff/submit_order/")
    public Map<String,String> submitOrder(@RequestParam Map<String, String> data) {
        return submitOrderService.submitOrder(data);
    }

}
