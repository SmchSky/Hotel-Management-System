package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.AddDishService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.DishInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddDishServiceImpl implements AddDishService {
    
    private final DishMapper dishMapper;
    
    @Autowired
    public AddDishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
    
    @Override
    public Map<String, String> addDish(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String name = data.get("name");
        String price = data.get("price");
        String status = data.get("status");
        // 合法性检验
        String message = DishInfoValidCheck.checkDishInfoValid(name, price, status);
        if(!message.equals("success")){
            map.put("message", message);
            return map;
        }
        // 获取当前编号最大的菜
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        String max_number = dishMapper.selectOne(queryWrapper.orderByDesc("number").last("LIMIT 1")).getNumber();
        // 取出数字
        int left_bit = Character.getNumericValue(max_number.charAt(0));
        int mid_bit = Character.getNumericValue(max_number.charAt(1));
        int right_bit = Character.getNumericValue(max_number.charAt(2));
        // 生成新菜品的编号
        right_bit += 1;
        if (right_bit == 10) {
            right_bit = 0;
            mid_bit += 1;
            if (mid_bit == 10) {
                mid_bit = 0;
                left_bit += 1;
                if (left_bit == 10) {
                    map.put("message", "菜品数量已达上限！");
                    return map;
                }
            }
        }
        String number = left_bit + String.valueOf(mid_bit) + right_bit;
        // 插入新菜品
        Dish dish = new Dish(number, name, Integer.parseInt(price), status);
        dishMapper.insert(dish);
        map.put("message", "success");
        return map;
    }
}
