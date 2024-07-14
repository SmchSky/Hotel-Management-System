package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetMenuListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMenuServiceImpl implements GetMenuListService {
    
    private final DishMapper dishMapper;

    @Autowired
    public GetMenuServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
    
    @Override
    public List<Dish> getMenu() {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        return dishMapper.selectList(queryWrapper.eq("status", "有库存"));
        
    }
}
