package com.hotelmanagementsystem.backend.service.impl.administrator.financial_staff;

import com.hotelmanagementsystem.backend.mapper.SalaryFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.SalaryFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.RegisterSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterSalaryServiceImpl implements RegisterSalaryService {
    
    private final SalaryFinanceRecordMapper salaryFinanceRecordMapper;
    
    @Autowired
    public RegisterSalaryServiceImpl(SalaryFinanceRecordMapper salaryFinanceRecordMapper) {
        this.salaryFinanceRecordMapper = salaryFinanceRecordMapper;
    }
    
    @Override
    public Map<String, String> registerSalary(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String staffNumber = data.get("number");
        String basic_salary = data.get("basic_salary");
        String extra_salary = data.get("extra_salary");
        if (extra_salary.isEmpty()) {
            map.put("message", "请输入额外工资！");
            return map;
        }
        String total_salary = "" + (Integer.parseInt(basic_salary) + Integer.parseInt(extra_salary));
        SalaryFinanceRecord salaryFinanceRecord = new SalaryFinanceRecord(staffNumber, basic_salary, extra_salary, total_salary, LocalDate.now());
        try {
            salaryFinanceRecordMapper.insert(salaryFinanceRecord);
        } catch (Exception e) {
            map.put("message", "同一天不能为同一个员工重复发工资！");
            return map;
        }
        map.put("message", "success");
        return map;
    }
}
