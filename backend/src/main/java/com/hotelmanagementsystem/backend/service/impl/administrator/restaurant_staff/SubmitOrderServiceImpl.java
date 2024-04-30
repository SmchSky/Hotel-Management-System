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


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmitOrderServiceImpl implements SubmitOrderService {
    @Autowired
    private DishOrderDetailMapper dishOrderDetailMapper;
    @Autowired
    private DishOrderRecordMapper dishOrderRecordMapper;
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private GeneralFinanceRecordMapper generalFinanceRecordMapper;

    @Override
    public Map<String, String> submitOrder(Map<String, String> data) {
        //定义返回的map
        Map<String, String> map = new HashMap<>();

        //将data中的数据取出放入new_data里面
        Map<String, Integer> new_data = new HashMap<>();
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 使用 TypeReference 定义目标类型为 Map<String, Integer>，其中键是字符串，值是整数
        TypeReference<List<List<Object>>> typeReference = new TypeReference<List<List<Object>>>() {};

        try {
            List<List<Object>> list = objectMapper.readValue(data.get("dishMap"), typeReference);
            // 调用 ObjectMapper 的 readValue 方法将处理后的字符串解析为目标类型的对象
            for (List<Object> item : list) {
                String key = (String) item.get(0);
                Integer value = (Integer) item.get(1);
                new_data.put(key, value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error_message", "出现错误");
            return map;
        }
        //生成订单编号
        //生成现在的时间，以字符串的形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_string = dateFormat.format(new java.util.Date());

        //利用正则表达式去除now_string中的连字符、冒号和空格
        String now_result = now_string.replaceAll("[-: ]", "");

        //尾缀02代表该订单编号的类型为餐饮订单
        String order_number = now_result.concat("02");

        //定义总价格
        int total_price = 0;

        //取出data中的数据，并插入到餐饮订单细节表
        for (Map.Entry<String, Integer> entry : new_data.entrySet()) {
            //获得菜品编号和数量
            String dishNumber = entry.getKey();
            Integer quantity = entry.getValue();

            //找到菜品
            QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("number", dishNumber);
            Dish dish = dishMapper.selectOne(queryWrapper);
            //计算这道菜品的总花销
            int price = dish.getPrice();
            total_price += price * quantity;

            //在订单细节表中插入这道菜品的信息
            DishOrderDetail dishOrderDetail = new DishOrderDetail(order_number, dishNumber, price, quantity);
            dishOrderDetailMapper.insert(dishOrderDetail);
        }
        //在餐饮订单记录表中插入一条餐饮订单
        DishOrderRecord dishOrderRecord = new DishOrderRecord(order_number, total_price, new java.util.Date());
        dishOrderRecordMapper.insert(dishOrderRecord);


        //在常规财务表中插入一条餐饮记录
        generalFinanceRecordMapper.insert(new GeneralFinanceRecord(order_number, "餐饮订单", total_price, new java.util.Date()));
        map.put("error_message", "success");
        return map;
    }
}
