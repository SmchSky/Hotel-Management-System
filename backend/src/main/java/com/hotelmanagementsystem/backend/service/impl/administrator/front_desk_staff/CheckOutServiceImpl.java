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

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckOutServiceImpl implements CheckOutService {
    @Autowired
    private LiveOrderRecordMapper liveOrderRecordMapper;
    @Autowired
    private GeneralFinanceRecordMapper generalFinanceRecordMapper;
    
    @Override
    public Map<String, String> checkOut(Map<String, String> data) {
        // 返回的map
        Map<String, String> map = new HashMap<>();
        // 取出入住记录的订单编号
        String number = data.get("number");
        System.out.println(number);
        // 在表中检索liveOrderRecord
        QueryWrapper<LiveOrderRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        LiveOrderRecord liveOrderRecord = liveOrderRecordMapper.selectOne(queryWrapper);
        // 更新liveOrderRecord
        UpdateWrapper<LiveOrderRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number).set("status", "已完成").set("checkout_date", Date.valueOf(LocalDate.now()));
        liveOrderRecordMapper.update(null, updateWrapper);
        // 以liveOrderRecord住宿订单记录为参考生成一条常规财务记录存入常规财务表中
        GeneralFinanceRecord generalFinanceRecord = new GeneralFinanceRecord(liveOrderRecord.getNumber(), "住宿订单", liveOrderRecord.getPrice(), liveOrderRecord.getFinishTime());
        generalFinanceRecordMapper.insert(generalFinanceRecord);
        // 返回的map
        map.put("error_message", "success");
        return map;
    }
}
