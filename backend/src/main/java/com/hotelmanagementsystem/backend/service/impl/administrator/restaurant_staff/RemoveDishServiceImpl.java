package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.RemoveDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RemoveDishServiceImpl implements RemoveDishService {

    private final DishMapper dishMapper;
    
    @Autowired
    public RemoveDishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    @Override
    public Map<String, String> removeDish(Map<String, String> data) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        dishMapper.delete(queryWrapper.eq("number", data.get("number")));
        return Map.of("message", "success");
    }
}
