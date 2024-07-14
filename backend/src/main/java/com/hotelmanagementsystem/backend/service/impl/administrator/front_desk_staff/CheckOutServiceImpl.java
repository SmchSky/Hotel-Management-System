package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.GeneralFinanceRecordMapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.pojo.GeneralFinanceRecord;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckOutServiceImpl implements CheckOutService {
    
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final GeneralFinanceRecordMapper generalFinanceRecordMapper;
    
    @Autowired
    public CheckOutServiceImpl(LiveOrderRecordMapper liveOrderRecordMapper, GeneralFinanceRecordMapper generalFinanceRecordMapper) {
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.generalFinanceRecordMapper = generalFinanceRecordMapper;
    }
    
    @Override
    public Map<String, String> checkOut(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        // 更新liveOrderRecord状态为"已完成"
        UpdateWrapper<LiveOrderRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number).set("status", "已完成").set("checkout_date", LocalDate.now());
        liveOrderRecordMapper.update(null, updateWrapper);
        // 查询出刚更新的住宿订单记录
        QueryWrapper<LiveOrderRecord> queryWrapper = new QueryWrapper<>();
        LiveOrderRecord liveOrderRecord = liveOrderRecordMapper.selectOne(queryWrapper.eq("number", number));
        // 以liveOrderRecord住宿订单记录为参考生成一条常规财务记录存入常规财务表中
        GeneralFinanceRecord generalFinanceRecord = new GeneralFinanceRecord(liveOrderRecord.getNumber(), "住宿订单", liveOrderRecord.getPrice(), liveOrderRecord.getFinishTime());
        generalFinanceRecordMapper.insert(generalFinanceRecord);
        map.put("message", "success");
        return map;
    }
}
