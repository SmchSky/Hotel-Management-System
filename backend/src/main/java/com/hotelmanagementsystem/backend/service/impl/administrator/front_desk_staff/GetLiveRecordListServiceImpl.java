package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetLiveRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetLiveRecordListServiceImpl implements GetLiveRecordListService {
    
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    
    @Autowired
    public GetLiveRecordListServiceImpl(LiveOrderRecordMapper liveOrderRecordMapper) {
        this.liveOrderRecordMapper = liveOrderRecordMapper;
    }

    @Override
    public Map<String, Object> getlist(Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        String resident_phone = data.get("resident_phone");
        //合法性检验
        if (resident_phone.length() != 11) {
            map.put("message", "手机号格式错误！");
            return map;
        }
        //在表中进行查询
        QueryWrapper<LiveOrderRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", resident_phone).eq("status", "已入住");
        List<LiveOrderRecord> list = liveOrderRecordMapper.selectList(queryWrapper);
        //如果没有订单记录
        if (list.isEmpty()) {
            map.put("message", "没有查找到相应的订单记录！");
            return map;
        }
        map.put("message", "success");
        map.put("live_record_list", list);
        return map;
    }
}
