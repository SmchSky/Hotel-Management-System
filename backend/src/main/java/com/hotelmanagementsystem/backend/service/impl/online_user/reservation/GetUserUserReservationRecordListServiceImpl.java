package com.hotelmanagementsystem.backend.service.impl.online_user.reservation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.impl.utils.OnlineUserDetailsImpl;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.GetUserReservationRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserUserReservationRecordListServiceImpl implements GetUserReservationRecordListService {

    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public List<ReservationRecord> getRecordList() {
        // authenticationToken来表示当前访问者的信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // 获取当事人信息对象loginUserDetails，本质为UserDetailsImpl类的一个实例
        OnlineUserDetailsImpl loginUserDetails = (OnlineUserDetailsImpl) authentication.getPrincipal();
        // 获取当前已登录的用户
        OnlineUser loginuser = loginUserDetails.getOnlineUser();

        //在表中查找记录并返回
        String username = loginuser.getUsername();
        QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return reservationRecordMapper.selectList(queryWrapper);
    }
}
