package com.hotelmanagementsystem.backend.service.impl.administrator.financial_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.GeneralFinanceRecordMapper;
import com.hotelmanagementsystem.backend.mapper.PurchaseFinanceRecordMapper;
import com.hotelmanagementsystem.backend.mapper.SalaryFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.GeneralFinanceRecord;
import com.hotelmanagementsystem.backend.pojo.PurchaseFinanceRecord;
import com.hotelmanagementsystem.backend.pojo.SalaryFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.GetRecordsService;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.QueryStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetRecordsServiceImpl implements GetRecordsService {

    @Autowired
    private GeneralFinanceRecordMapper generalFinanceRecordMapper;
    @Autowired
    private PurchaseFinanceRecordMapper purchaseFinanceRecordMapper;
    @Autowired
    private SalaryFinanceRecordMapper salaryFinanceRecordMapper;

    @Override
    public Map<String, Object> getRecords(Map<String, String> data) {
        //返回的map
        Map<String, Object> map = new HashMap<>();
        //获取信息
        String type = data.get("type");
        //判断
        if ("常规财务".equals(type)) {
            QueryWrapper<GeneralFinanceRecord> queryWrapper = new QueryWrapper<>();
            List<GeneralFinanceRecord> list = generalFinanceRecordMapper.selectList(queryWrapper);
            map.put("error_message", "success");
            map.put("records", list);
        }
        if("采购财务".equals(type)){
            QueryWrapper<PurchaseFinanceRecord> queryWrapper = new QueryWrapper<>();
            List<PurchaseFinanceRecord> list = purchaseFinanceRecordMapper.selectList(queryWrapper);
            map.put("error_message", "success");
            map.put("records", list);
        }
        if("工资财务".equals(type)){
            QueryWrapper<SalaryFinanceRecord> queryWrapper = new QueryWrapper<>();
            List<SalaryFinanceRecord> list = salaryFinanceRecordMapper.selectList(queryWrapper);
            map.put("error_message", "success");
            map.put("records", list);
        }
        return map;
    }
}
