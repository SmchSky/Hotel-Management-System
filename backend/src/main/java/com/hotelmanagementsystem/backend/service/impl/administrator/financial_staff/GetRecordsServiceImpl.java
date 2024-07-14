package com.hotelmanagementsystem.backend.service.impl.administrator.financial_staff;

import com.hotelmanagementsystem.backend.mapper.GeneralFinanceRecordMapper;
import com.hotelmanagementsystem.backend.mapper.PurchaseFinanceRecordMapper;
import com.hotelmanagementsystem.backend.mapper.SalaryFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.GeneralFinanceRecord;
import com.hotelmanagementsystem.backend.pojo.PurchaseFinanceRecord;
import com.hotelmanagementsystem.backend.pojo.SalaryFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.GetRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetRecordsServiceImpl implements GetRecordsService {
    
    private final GeneralFinanceRecordMapper generalFinanceRecordMapper;
    private final PurchaseFinanceRecordMapper purchaseFinanceRecordMapper;
    private final SalaryFinanceRecordMapper salaryFinanceRecordMapper;
    
    @Autowired
    public GetRecordsServiceImpl(GeneralFinanceRecordMapper generalFinanceRecordMapper, PurchaseFinanceRecordMapper purchaseFinanceRecordMapper, SalaryFinanceRecordMapper salaryFinanceRecordMapper) {
        this.generalFinanceRecordMapper = generalFinanceRecordMapper;
        this.purchaseFinanceRecordMapper = purchaseFinanceRecordMapper;
        this.salaryFinanceRecordMapper = salaryFinanceRecordMapper;
    }
    
    @Override
    public Map<String, Object> getRecords(Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        String type = data.get("type");
        switch (type) {
            case "常规财务" -> {
                List<GeneralFinanceRecord> list = generalFinanceRecordMapper.selectList(null);
                map.put("message", "success");
                map.put("records", list);
                return map;
            }
            case "采购财务" -> {
                List<PurchaseFinanceRecord> list = purchaseFinanceRecordMapper.selectList(null);
                map.put("message", "success");
                map.put("records", list);
                return map;
            }
            case "工资财务" -> {
                List<SalaryFinanceRecord> list = salaryFinanceRecordMapper.selectList(null);
                map.put("message", "success");
                map.put("records", list);
                return map;
            }
            default -> {
                map.put("message", "error");
                return map;
            }
        }
    }
}
