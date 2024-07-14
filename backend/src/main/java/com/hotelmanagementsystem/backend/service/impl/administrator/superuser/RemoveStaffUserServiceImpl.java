package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.RemoveStaffUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveStaffUserServiceImpl implements RemoveStaffUserService {
    
    private final FinancialStaffMapper financialStaffMapper;
    private final FrontDeskStaffMapper frontDeskStaffMapper;
    private final HrStaffMapper hrStaffMapper;
    private final PurchaseStaffMapper purchaseStaffMapper;
    private final RestaurantStaffMapper restaurantStaffMapper;
    private final StaffMapper staffMapper;
    
    @Autowired
    public RemoveStaffUserServiceImpl(FinancialStaffMapper financialStaffMapper, FrontDeskStaffMapper frontDeskStaffMapper, HrStaffMapper hrStaffMapper, PurchaseStaffMapper purchaseStaffMapper, RestaurantStaffMapper restaurantStaffMapper, StaffMapper staffMapper) {
        this.financialStaffMapper = financialStaffMapper;
        this.frontDeskStaffMapper = frontDeskStaffMapper;
        this.hrStaffMapper = hrStaffMapper;
        this.purchaseStaffMapper = purchaseStaffMapper;
        this.restaurantStaffMapper = restaurantStaffMapper;
        this.staffMapper = staffMapper;
    }
    
    /**
     * 有时间可以看一下是不是先删员工信息表再删员工用户表更方便！！！
     */
    @Override
    public Map<String, String> removeStaffUser(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String username = data.get("username");
        String duty = data.get("duty");
        
        // 删除员工用户并获取员工编号
        String number = removeStaffUserByDuty(duty, username);
        if (number == null) {
            map.put("message", "该用户不存在！");
            return map;
        }
        // 从员工表中删除对应的员工信息
        staffMapper.delete(new QueryWrapper<Staff>().eq("number", number));
        map.put("message", "success");
        return map;
    }
    
    private String removeStaffUserByDuty(String duty, String username) {
        return switch (duty) {
            case "酒店前台工作人员" ->
                    removeUser(frontDeskStaffMapper, new QueryWrapper<FrontDeskStaffUser>().eq("username", username));
            case "餐厅前台工作人员" ->
                    removeUser(restaurantStaffMapper, new QueryWrapper<RestaurantStaffUser>().eq("username", username));
            case "财务管理员" ->
                    removeUser(financialStaffMapper, new QueryWrapper<FinancialStaffUser>().eq("username", username));
            case "人事管理员" -> removeUser(hrStaffMapper, new QueryWrapper<HrStaffUser>().eq("username", username));
            case "采购人员" ->
                    removeUser(purchaseStaffMapper, new QueryWrapper<PurchaseStaffUser>().eq("username", username));
            default -> null;
        };
    }
    
    private <T extends User> String removeUser(BaseMapper<T> mapper, QueryWrapper<T> queryWrapper) {
        User user = mapper.selectOne(queryWrapper);
        if (user != null) {
            try {
                Method method_getNumber = user.getClass().getMethod("getNumber");
                String number = (String) method_getNumber.invoke(user);
                mapper.delete(queryWrapper);
                return number;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}