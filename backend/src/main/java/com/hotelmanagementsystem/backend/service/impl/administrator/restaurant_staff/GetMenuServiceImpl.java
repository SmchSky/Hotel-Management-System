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
    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<Dish> getMenu() {

        //获得有库存的菜品
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "有库存");
        //定义返回的list
        List<Dish> list = dishMapper.selectList(queryWrapper);
        return list;
    }
}
