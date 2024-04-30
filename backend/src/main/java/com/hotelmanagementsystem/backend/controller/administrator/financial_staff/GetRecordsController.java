package com.hotelmanagementsystem.backend.controller.administrator.financial_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.GetRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRecordsController {
    @Autowired
    private GetRecordsService getRecordsService;

    //由于不需要修改数据库，故使用GetMapping
    @GetMapping("/financial_staff/get_records/")
    public Map<String,Object> getRecords(@RequestParam Map<String, String> data) {
        return getRecordsService.getRecords(data);
    }
}
