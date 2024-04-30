package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.RemoveDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveDishServiceImpl implements RemoveDishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public Map<String, String> removeDish(Map<String, String> data) {
        //定义返回的map
        Map<String, String> map = new HashMap<>();
        //取出data的数据
        String number = data.get("number");
        //找到要删除的菜品
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        dishMapper.delete(queryWrapper);
        //返回信息
        map.put("error_message", "success");
        return map;
    }
}
