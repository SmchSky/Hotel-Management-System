package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.RemoveStaffUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class RemoveStaffUserServiceImpl implements RemoveStaffUserService {
    @Autowired
    private FinancialStaffMapper financialStaffMapper;
    @Autowired
    private FrontDeskStaffMapper frontDeskStaffMapper;
    @Autowired
    private HrStaffMapper hrStaffMapper;
    @Autowired
    private PurchaseStaffMapper purchaseStaffMapper;
    @Autowired
    private RestaurantStaffMapper restaurantStaffMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Map<String, String> removeStaffUser(Map<String, String> data) {
        //用于返回的map
        Map<String, String> map = new HashMap<>();

        String username = data.get("username");
        String duty = data.get("duty");
        String number = null;

        //从相应员工用户表中删除user
        if ("酒店前台工作人员".equals(duty)) {
            QueryWrapper<FrontDeskStaff> frontDeskStaffQueryWrapper = new QueryWrapper<>();
            frontDeskStaffQueryWrapper.eq("username", username);
            //取出员工编号
            number = frontDeskStaffMapper.selectOne(frontDeskStaffQueryWrapper).getNumber();
            //删除
            frontDeskStaffMapper.delete(frontDeskStaffQueryWrapper);
        } else if ("餐厅前台工作人员".equals(duty)) {
            QueryWrapper<RestaurantStaff> restaurantStaffQueryWrapper = new QueryWrapper<>();
            restaurantStaffQueryWrapper.eq("username", username);
            //取出员工编号
            number = restaurantStaffMapper.selectOne(restaurantStaffQueryWrapper).getNumber();
            //删除
            restaurantStaffMapper.delete(restaurantStaffQueryWrapper);
        } else if ("财务管理员".equals(duty)) {
            QueryWrapper<FinancialStaff> financialStaffQueryWrapper = new QueryWrapper<>();
            financialStaffQueryWrapper.eq("username", username);
            //取出员工编号
            number = financialStaffMapper.selectOne(financialStaffQueryWrapper).getNumber();
            //删除
            financialStaffMapper.delete(financialStaffQueryWrapper);
        } else if ("人事管理员".equals(duty)) {
            QueryWrapper<HrStaff> hrStaffQueryWrapper = new QueryWrapper<>();
            hrStaffQueryWrapper.eq("username", username);
            //取出员工编号
            number = hrStaffMapper.selectOne(hrStaffQueryWrapper).getNumber();
            //删除
            hrStaffMapper.delete(hrStaffQueryWrapper);
        } else if ("采购人员".equals(duty)) {
            QueryWrapper<PurchaseStaff> purchaseStaffQueryWrapper = new QueryWrapper<>();
            purchaseStaffQueryWrapper.eq("username", username);
            //取出员工编号
            number = purchaseStaffMapper.selectOne(purchaseStaffQueryWrapper).getNumber();
            //删除
            purchaseStaffMapper.delete(purchaseStaffQueryWrapper);
        }

        //从员工表中删除对应的员工信息
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        staffMapper.delete(queryWrapper);

        map.put("error_message", "success");
        return map;
    }
}
