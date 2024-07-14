package com.hotelmanagementsystem.backend.service.impl.administrator.purchase_staff;

import com.hotelmanagementsystem.backend.mapper.PurchaseFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.PurchaseFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.purchase_staff.RegisterStuffService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.RegisterStaffInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterStuffServiceImpl implements RegisterStuffService {
    
    private final PurchaseFinanceRecordMapper purchaseFinanceRecordMapper;
    
    @Autowired
    public RegisterStuffServiceImpl(PurchaseFinanceRecordMapper purchaseFinanceRecordMapper) {
        this.purchaseFinanceRecordMapper = purchaseFinanceRecordMapper;
    }
    
    @Override
    public Map<String, String> registerStuff(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String stuff_name = data.get("stuff_name");
        String quantity = data.get("quantity");
        String price = data.get("price");
        String position = data.get("position");
        String purchase_date = data.get("date");
        String staff_name = data.get("staff_name");
        String confirmed_staff_name = data.get("confirmed_staff_name");
        
        // 合法性检验
        String message = RegisterStaffInfoValidCheck.checkRegisterStaffInfoValid(stuff_name, quantity, price, position, purchase_date, staff_name, confirmed_staff_name);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        
        // 采购编号格式为当前时间字符串+尾缀04
        String purchaseNumber = LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd " + "HH:mm:ss").toFormatter()).replaceAll("[-: ]", "").concat("04");
        // 新建采购财务记录并插入表中
        PurchaseFinanceRecord purchaseFinanceRecord = new PurchaseFinanceRecord(purchaseNumber, stuff_name, quantity, price, LocalDate.parse(purchase_date), position, staff_name, confirmed_staff_name);
        purchaseFinanceRecordMapper.insert(purchaseFinanceRecord);
        map.put("message", "success");
        return map;
    }
}
