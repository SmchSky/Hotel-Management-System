package com.hotelmanagementsystem.backend.service.impl.administrator.purchase_staff;

import com.hotelmanagementsystem.backend.mapper.PurchaseFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.PurchaseFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.purchase_staff.RegisterStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterStuffServiceImpl implements RegisterStuffService {
    @Autowired
    private PurchaseFinanceRecordMapper purchaseFinanceRecordMapper;

    @Override
    public Map<String, String> registerStuff(Map<String, String> data) {
        //返回的map
        Map<String, String> map = new HashMap<>();
        //取出信息
        String stuff_name = data.get("stuff_name");
        String quantity = data.get("quantity");
        String price = data.get("price");
        String position = data.get("position");
        String date_string = data.get("date");
        String staff_name = data.get("staff_name");
        String confirmed_staff_name = data.get("confirmed_staff_name");
        //合法性检验
        if (stuff_name.length() == 0) {
            map.put("error_message", "产品名称不能为空！");
            return map;
        }
        if (quantity.length() == 0) {
            map.put("error_message", "采购数量不能为空！");
            return map;
        }
        if (position.length() == 0) {
            map.put("error_message", "采购地点不能为空！");
            return map;
        }
        if (price.length() == 0) {
            map.put("error_message", "采购费用不能为空！");
            return map;
        }
        if (date_string.length() == 0) {
            map.put("error_message", "采购日期不能为空！");
            return map;
        }
        if (staff_name.length() == 0) {
            map.put("error_message", "采购人员姓名不能为空！");
            return map;
        }
        if (confirmed_staff_name.length() == 0) {
            map.put("error_message", "批准采购的人员姓名不能为空！");
            return map;
        }

        //将date_string转换为Date类型
        Date date = Date.valueOf(date_string);

        //生成现在的时间，以字符串的形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_string = dateFormat.format(new java.util.Date());

        //利用正则表达式去除now_string中的连字符、冒号和空格
        String now_result = now_string.replaceAll("[-: ]", "");

        //尾缀03代表该订单编号的类型为采购编号
        String order_number = now_result.concat("03");

        //新建采购财务记录并插入表中
        PurchaseFinanceRecord purchaseFinanceRecord = new PurchaseFinanceRecord(order_number, stuff_name, quantity, price, date, position, staff_name, confirmed_staff_name);
        purchaseFinanceRecordMapper.insert(purchaseFinanceRecord);
        //返回map
        map.put("error_message", "success");
        return map;
    }
}
