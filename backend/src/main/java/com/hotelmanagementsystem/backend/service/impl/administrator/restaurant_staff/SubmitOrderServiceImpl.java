package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.mapper.DishOrderDetailMapper;
import com.hotelmanagementsystem.backend.mapper.DishOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.GeneralFinanceRecordMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.pojo.DishOrderDetail;
import com.hotelmanagementsystem.backend.pojo.DishOrderRecord;
import com.hotelmanagementsystem.backend.pojo.GeneralFinanceRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.SubmitOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类的逻辑未修改，留待以后有时间修改
 */
@Service
public class SubmitOrderServiceImpl implements SubmitOrderService {
    
    private final DishOrderDetailMapper dishOrderDetailMapper;
    private final DishOrderRecordMapper dishOrderRecordMapper;
    private final DishMapper dishMapper;
    private final GeneralFinanceRecordMapper generalFinanceRecordMapper;
    
    @Autowired
    public SubmitOrderServiceImpl(DishOrderDetailMapper dishOrderDetailMapper, DishOrderRecordMapper dishOrderRecordMapper, DishMapper dishMapper, GeneralFinanceRecordMapper generalFinanceRecordMapper) {
        this.dishOrderDetailMapper = dishOrderDetailMapper;
        this.dishOrderRecordMapper = dishOrderRecordMapper;
        this.dishMapper = dishMapper;
        this.generalFinanceRecordMapper = generalFinanceRecordMapper;
    }
    
    @Override
    public Map<String, String> submitOrder(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        // 将data中的数据取出放入new_data里面
        Map<String, Integer> new_data = new HashMap<>();
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 使用 TypeReference 定义目标类型为 Map<String, Integer>，其中键是字符串，值是整数
        TypeReference<List<List<Object>>> typeReference = new TypeReference<>() {
        };
        
        try {
            List<List<Object>> list = objectMapper.readValue(data.get("dishMap"), typeReference);
            // 调用 ObjectMapper 的 readValue 方法将处理后的字符串解析为目标类型的对象
            for (List<Object> item : list) {
                String key = (String) item.get(0);
                Integer value = (Integer) item.get(1);
                new_data.put(key, value);
            }
        } catch (Exception e) {
            map.put("message", "出现错误");
            return map;
        }
        
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 餐饮订单编号格式为当前时间字符串+尾缀02
        String orderNumber = now.format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd " + "HH:mm:ss").toFormatter()).replaceAll("[-: ]", "").concat("02");
        
        int total_price = 0;
        // 取出data中的数据，并插入到餐饮订单细节表
        for (Map.Entry<String, Integer> entry : new_data.entrySet()) {
            // 获得菜品编号和数量
            String dishNumber = entry.getKey();
            Integer quantity = entry.getValue();
            
            // 找到菜品
            QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("number", dishNumber);
            Dish dish = dishMapper.selectOne(queryWrapper);
            // 计算这道菜品的总花销
            int price = dish.getPrice();
            total_price += price * quantity;
            
            // 在订单细节表中插入这道菜品的信息
            DishOrderDetail dishOrderDetail = new DishOrderDetail(orderNumber, dishNumber, price, quantity);
            dishOrderDetailMapper.insert(dishOrderDetail);
        }
        // 在餐饮订单记录表中插入一条餐饮订单
        DishOrderRecord dishOrderRecord = new DishOrderRecord(orderNumber, total_price, now);
        dishOrderRecordMapper.insert(dishOrderRecord);
        
        // 在常规财务表中插入一条餐饮记录
        generalFinanceRecordMapper.insert(new GeneralFinanceRecord(orderNumber, "餐饮订单", total_price, now));
        map.put("message", "success");
        return map;
    }
}
