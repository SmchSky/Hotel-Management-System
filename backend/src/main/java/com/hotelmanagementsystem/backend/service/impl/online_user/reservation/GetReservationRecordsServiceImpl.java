package com.hotelmanagementsystem.backend.service.impl.online_user.reservation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.GetReservationRecordsService;
import com.hotelmanagementsystem.backend.utils.user_details.OnlineUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetReservationRecordsServiceImpl implements GetReservationRecordsService {
    
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public GetReservationRecordsServiceImpl(ReservationRecordMapper reservationRecordMapper) {
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    @Override
    public List<ReservationRecord> getRecordList() {
        // 获取token对应的用户的用户名
        String username = ((OnlineUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUsername();
        QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return reservationRecordMapper.selectList(queryWrapper);
    }
}
