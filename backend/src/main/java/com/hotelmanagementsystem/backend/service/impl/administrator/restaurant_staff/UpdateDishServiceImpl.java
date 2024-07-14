package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.UpdateDishService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.DishInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateDishServiceImpl implements UpdateDishService {
    
    private final DishMapper dishMapper;
    
    @Autowired
    public UpdateDishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    @Override
    public Map<String, String> updateDish(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        String name = data.get("name");
        String price = data.get("price");
        String status = data.get("status");
        // 合法性检验
        String message = DishInfoValidCheck.checkDishInfoValid(name, price, status);
        if(!message.equals("success")){
            map.put("message", message);
            return map;
        }
        //更新菜品
        UpdateWrapper<Dish> updateWrapper = new UpdateWrapper<>();
        Dish dish = new Dish(number, name, Integer.parseInt(price), status);
        dishMapper.update(dish, updateWrapper.eq("number", number));
        map.put("message", "success");
        return map;
    }
}
