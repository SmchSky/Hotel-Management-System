package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetAllDishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllDishListServiceImpl implements GetAllDishListService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<Dish> getAllDishList() {

        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        return dishMapper.selectList(queryWrapper);

    }
}
