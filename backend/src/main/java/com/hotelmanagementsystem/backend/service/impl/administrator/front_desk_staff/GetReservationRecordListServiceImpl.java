package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetReservationRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetReservationRecordListServiceImpl implements GetReservationRecordListService {
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public Map<String, Object> getList(Map<String, String> data) {
        //返回的map
        Map<String, Object> map = new HashMap<>();
        //获取信息
        String resident_phone = data.get("resident_phone");
        //合法性检验
        if (resident_phone.length() != 11) {
            map.put("error_message", "手机号格式错误！");
            return map;
        }
        //在表中进行查询
        QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resident_phone", resident_phone).eq("status", "等待入住");
        List<ReservationRecord> list = reservationRecordMapper.selectList(queryWrapper);
        //如果没有预约记录
        if (list.isEmpty()) {
            map.put("error_message", "没有查找到相应的预约记录！");
            return map;
        }
        //返回map
        map.put("error_message", "success");
        map.put("reservation_record_list", list);
        return map;
    }
}
