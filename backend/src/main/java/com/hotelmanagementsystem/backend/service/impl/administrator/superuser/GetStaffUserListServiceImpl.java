package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.GetStaffUserListSercvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStaffUserListServiceImpl implements GetStaffUserListSercvice {

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

    @Override
    public List<StaffUser> getStaffUserList() {

        //用于返回的users
        List<StaffUser> users = new ArrayList<>();

        QueryWrapper<FrontDeskStaff> frontDeskStaffQueryWrapper = new QueryWrapper<>();
        users.addAll(frontDeskStaffMapper.selectList(frontDeskStaffQueryWrapper));

        QueryWrapper<FinancialStaff> financialStaffQueryWrapper = new QueryWrapper<>();
        users.addAll(financialStaffMapper.selectList(financialStaffQueryWrapper));

        QueryWrapper<HrStaff> hrStaffQueryWrapper = new QueryWrapper<>();
        users.addAll(hrStaffMapper.selectList(hrStaffQueryWrapper));

        QueryWrapper<PurchaseStaff> purchaseStaffQueryWrapper = new QueryWrapper<>();
        users.addAll(purchaseStaffMapper.selectList(purchaseStaffQueryWrapper));

        QueryWrapper<RestaurantStaff> restaurantStaffQueryWrapper = new QueryWrapper<>();
        users.addAll(restaurantStaffMapper.selectList(restaurantStaffQueryWrapper));

        //返回结果
        return users;
    }
}
