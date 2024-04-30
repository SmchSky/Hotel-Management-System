package com.hotelmanagementsystem.backend.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTask {
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Scheduled(cron = "0 0 0 * * ?") // 每天0点执行任务
    public void updateReservationRecordStatus() {
        // 检查预约表中所有“状态”等于“等待入住”元组，判断该元组的“入住日期”字段值是否已经小于当前日期，如果是，则把该元组的“状态”字段值改为“已超期”
        QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "等待入住");
        List<ReservationRecord> list = reservationRecordMapper.selectList(queryWrapper);

        LocalDate currentDate = LocalDate.now();
        //遍历list
        for (ReservationRecord reservationRecord : list) {
            if (currentDate.isAfter(reservationRecord.getCheckinDate().toLocalDate())) {
                UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number",reservationRecord.getNumber()).set("status", "已超期");
                reservationRecordMapper.update(null, updateWrapper);
            }
        }
    }
}
