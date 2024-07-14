package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetAllDishListService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetAllDishListController {
    
    private final GetAllDishListService getAllDishListService;
    
    @Autowired
    public GetAllDishListController(GetAllDishListService getAllDishListService) {
        this.getAllDishListService = getAllDishListService;
    }

    @GetMapping("/restaurant_staff/get_all_dish_list")
    public List<Dish> getMenu() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("餐厅前台工作人员")) {
            return null;
        }
        return getAllDishListService.getAllDishList();
    }
}
