package com.hotelmanagementsystem.backend.service.impl.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.mapper.DishMapper;
import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetAllDishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDishListServiceImpl implements GetAllDishListService {
    
    private final DishMapper dishMapper;
    
    @Autowired
    public GetAllDishListServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
    
    @Override
    public List<Dish> getAllDishList() {
        return dishMapper.selectList(null);
        
    }
}
