package com.hotelmanagementsystem.backend.service.impl.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private HttpServletRequest request;  //用于获取AJAX请求中的参数
    @Autowired
    private OnlineUserMapper onlineUserMapper;
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
    private SuperuserMapper superuserMapper;

    @Override
    //按用户名查找用户信息
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取用户职务信息
        String duty = request.getParameter("duty");

        ////用于调试
        //System.out.println(duty);

        //用户身份为OnlineUser，在online_user表中进行查询验证
        if (duty == null) {
            QueryWrapper<OnlineUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            OnlineUser onlineUser = onlineUserMapper.selectOne(queryWrapper);
            if (onlineUser == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new OnlineUserDetailsImpl(onlineUser);  //返回该用户的细节信息
        }

        if ("酒店前台工作人员".equals(duty)) {
            QueryWrapper<FrontDeskStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            FrontDeskStaff frontDeskStaff = frontDeskStaffMapper.selectOne(queryWrapper);
            if (frontDeskStaff == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new FrontDeskStaffStaffUserDetailsImpl(frontDeskStaff);
        }

        if ("餐厅前台工作人员".equals(duty)) {
            QueryWrapper<RestaurantStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            RestaurantStaff restaurantStaff = restaurantStaffMapper.selectOne(queryWrapper);
            if (restaurantStaff == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new RestaurantStaffStaffUserDetailsImpl(restaurantStaff);
        }

        if ("财务管理员".equals(duty)) {
            QueryWrapper<FinancialStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            FinancialStaff financialStaff = financialStaffMapper.selectOne(queryWrapper);
            if (financialStaff == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new FinancialStaffStaffUserDetailsImpl(financialStaff);
        }

        if ("人事管理员".equals(duty)) {
            QueryWrapper<HrStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            HrStaff hrStaff = hrStaffMapper.selectOne(queryWrapper);
            if (hrStaff == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new HrStaffStaffUserDetailsImpl(hrStaff);
        }

        if ("采购人员".equals(duty)) {
            QueryWrapper<PurchaseStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            PurchaseStaff purchaseStaff = purchaseStaffMapper.selectOne(queryWrapper);
            if (purchaseStaff == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new PurchaseStaffStaffUserDetailsImpl(purchaseStaff);
        }

        if ("超级用户".equals(duty)) {
            QueryWrapper<Superuser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            Superuser superuser = superuserMapper.selectOne(queryWrapper);
            if (superuser == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return new SuperUserDetailsImpl(superuser);
        }

        //其它情况
        throw new UsernameNotFoundException("用户身份错误！");
    }
}
