package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.User;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.GetStaffUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStaffUserListServiceImpl implements GetStaffUserListService {

    private final FinancialStaffMapper financialStaffMapper;
    private final FrontDeskStaffMapper frontDeskStaffMapper;
    private final HrStaffMapper hrStaffMapper;
    private final PurchaseStaffMapper purchaseStaffMapper;
    private final RestaurantStaffMapper restaurantStaffMapper;
    
    @Autowired
    public GetStaffUserListServiceImpl(FinancialStaffMapper financialStaffMapper, FrontDeskStaffMapper frontDeskStaffMapper, HrStaffMapper hrStaffMapper, PurchaseStaffMapper purchaseStaffMapper, RestaurantStaffMapper restaurantStaffMapper) {
        this.financialStaffMapper = financialStaffMapper;
        this.frontDeskStaffMapper = frontDeskStaffMapper;
        this.hrStaffMapper = hrStaffMapper;
        this.purchaseStaffMapper = purchaseStaffMapper;
       this.restaurantStaffMapper = restaurantStaffMapper;
   }

    @Override
    public List<User> getStaffUserList() {
        List<User> users = new ArrayList<>();
        users.addAll(frontDeskStaffMapper.selectList(null));
        users.addAll(financialStaffMapper.selectList(null));
        users.addAll(hrStaffMapper.selectList(null));
        users.addAll(purchaseStaffMapper.selectList(null));
        users.addAll(restaurantStaffMapper.selectList(null));
        return users;
    }
}
