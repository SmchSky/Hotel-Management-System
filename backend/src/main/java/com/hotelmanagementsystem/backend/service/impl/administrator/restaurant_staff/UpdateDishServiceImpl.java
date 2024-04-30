package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.UpdateDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateDishServiceImpl implements UpdateDishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public Map<String, String> updateDish(Map<String, String> data) {
        //定义返回的map
        Map<String, String> map = new HashMap<>();
        //取出data数据
        String number = data.get("number");
        String name = data.get("name");
        String price = data.get("price");
        String status = data.get("status");

        //合法性检验
        if (name.length() == 0) {
            map.put("error_message", "菜品名称不可为空！");
            return map;
        }
        if (name.contains(" ")) {
            map.put("error_message", "菜品名称不可包含空格！");
            return map;
        }
        if (price.length() == 0) {
            map.put("error_message", "菜品价格不可为空！");
            return map;
        }
        //更新菜品
        UpdateWrapper<Dish> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number);
        Dish dish = new Dish(number, name, Integer.parseInt(price), status);
        dishMapper.update(dish, updateWrapper);
        map.put("error_message", "success");
        return map;
    }
}
