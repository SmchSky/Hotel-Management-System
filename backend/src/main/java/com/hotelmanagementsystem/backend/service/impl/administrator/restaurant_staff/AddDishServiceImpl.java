package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.AddDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddDishServiceImpl implements AddDishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public Map<String, String> addDish(Map<String, String> data) {
        //定义返回的map
        Map<String, String> map = new HashMap<>();
        //取出data的信息
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
        //获取当前编号最大的菜
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("number");
        queryWrapper.last("LIMIT 1");
        Dish max_number_dish = dishMapper.selectOne(queryWrapper);

        System.out.println(max_number_dish.getNumber());
        //获取最大编号
        int left_bit = Character.getNumericValue(max_number_dish.getNumber().charAt(0));
        int mid_bit = Character.getNumericValue(max_number_dish.getNumber().charAt(1));
        int right_bit = Character.getNumericValue(max_number_dish.getNumber().charAt(2));

        //生成新菜品的编号
        right_bit += 1;
        if (right_bit == 10) {
            right_bit = 0;
            mid_bit += 1;
            if (mid_bit == 10) {
                left_bit += 1;
                if (left_bit == 10) {
                    map.put("error_message", "菜品数量已达上限！");
                    return map;
                }
            }
        }
        String number = left_bit + String.valueOf(mid_bit) + right_bit;
        //插入新菜品
        Dish dish = new Dish(number, name, Integer.parseInt(price), status);
        dishMapper.insert(dish);
        map.put("error_message", "success");
        return map;
    }
}
