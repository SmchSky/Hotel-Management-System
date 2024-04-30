package com.hotelmanagementsystem.backend.service.impl.administrator.financial_staff;

import com.hotelmanagementsystem.backend.mapper.SalaryFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.SalaryFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.RegisterSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterSalaryServiceImpl implements RegisterSalaryService {
    @Autowired
    SalaryFinanceRecordMapper salaryFinanceRecordMapper;
    
    @Override
    public Map<String, String> registerSalary(Map<String, String> data) {
        // 返回的map
        Map<String, String> map = new HashMap<>();
        // 取出data的数据
        String number = data.get("number");
        String basic_salary = data.get("basic_salary");
        String extra_salary = data.get("extra_salary");
        // 如果额外工资为null
        if (extra_salary.equals("")) {
            map.put("error_message", "请输入额外工资！");
            return map;
        }
        // 计算总工资
        String total_salary = "" + (Integer.parseInt(basic_salary) + Integer.parseInt(extra_salary));
        // 获取当前日期
        java.util.Date currentDate = new java.util.Date();
        // 转换为java.sql.Date类型
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        // 定义对象
        SalaryFinanceRecord salaryFinanceRecord = new SalaryFinanceRecord(number, basic_salary, extra_salary, total_salary, sqlDate);
        // 插入表中
        try {
            salaryFinanceRecordMapper.insert(salaryFinanceRecord);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            map.put("error_message", "同一天不能为同一个员工重复发工资！");
            return map;
        }
        map.put("error_message", "success");
        return map;
    }
}
