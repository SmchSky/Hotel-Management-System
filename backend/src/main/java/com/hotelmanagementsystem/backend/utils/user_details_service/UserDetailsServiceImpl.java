package com.hotelmanagementsystem.backend.utils.user_details_service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.utils.user_details.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final HttpServletRequest request;
    private final OnlineUserMapper onlineUserMapper;
    private final FrontDeskStaffMapper frontDeskStaffMapper;
    private final FinancialStaffMapper financialStaffMapper;
    private final HrStaffMapper hrStaffMapper;
    private final RestaurantStaffMapper restaurantStaffMapper;
    private final PurchaseStaffMapper purchaseStaffMapper;
    private final SuperuserMapper superuserMapper;
    
    @Autowired
    public UserDetailsServiceImpl(HttpServletRequest request, OnlineUserMapper onlineUserMapper, FrontDeskStaffMapper frontDeskStaffMapper, FinancialStaffMapper financialStaffMapper, HrStaffMapper hrStaffMapper, RestaurantStaffMapper restaurantStaffMapper, PurchaseStaffMapper purchaseStaffMapper, SuperuserMapper superuserMapper) {
        this.request = request;
        this.onlineUserMapper = onlineUserMapper;
        this.frontDeskStaffMapper = frontDeskStaffMapper;
        this.financialStaffMapper = financialStaffMapper;
        this.hrStaffMapper = hrStaffMapper;
        this.restaurantStaffMapper = restaurantStaffMapper;
        this.purchaseStaffMapper = purchaseStaffMapper;
        this.superuserMapper = superuserMapper;
    }
    
    /**
     * 按用户名在数据库中查找用户信息
     *
     * @param username 用户名
     * @return 该用户名对应的UserDetails对象
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户职务信息
        String duty = request.getParameter("duty");
        // 用户实体对象
        User user;
        
        // 身份为线上用户
        if (duty == null) {
            QueryWrapper<OnlineUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            user = onlineUserMapper.selectOne(queryWrapper);
            if (user == null) {
                throw new UsernameNotFoundException("用户名不存在！");
            }
            return new OnlineUserDetails((OnlineUser) user, "线上用户");
        }
        // 身份为酒店工作人员
        switch (duty) {
            case "酒店前台工作人员" -> {
                QueryWrapper<FrontDeskStaffUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = frontDeskStaffMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new FrontDeskStaffUserDetails((FrontDeskStaffUser) user, "酒店前台工作人员");
            }
            case "餐厅前台工作人员" -> {
                QueryWrapper<RestaurantStaffUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = restaurantStaffMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new RestaurantStaffUserDetails((RestaurantStaffUser) user, "餐厅前台工作人员");
            }
            case "财务管理员" -> {
                QueryWrapper<FinancialStaffUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = financialStaffMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new FinancialStaffUserDetails((FinancialStaffUser) user, "财务管理员");
            }
            case "人事管理员" -> {
                QueryWrapper<HrStaffUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = hrStaffMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new HrStaffUserDetails((HrStaffUser) user, "人事管理员");
            }
            case "采购人员" -> {
                QueryWrapper<PurchaseStaffUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = purchaseStaffMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new PurchaseStaffUserDetails((PurchaseStaffUser) user, "采购人员");
            }
            case "超级用户" -> {
                QueryWrapper<Superuser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                user = superuserMapper.selectOne(queryWrapper);
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在！");
                }
                return new SuperUserDetails((Superuser) user, "超级用户");
            }
            default -> throw new UsernameNotFoundException("用户职务类型错误！");
        }
    }
}
