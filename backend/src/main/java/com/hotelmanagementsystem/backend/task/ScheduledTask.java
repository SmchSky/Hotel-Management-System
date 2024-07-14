package com.hotelmanagementsystem.backend.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 定时任务类
 */
@Component
public class ScheduledTask {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public ScheduledTask(ReservationRecordMapper reservationRecordMapper) {
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    /**
     * 每日0点更新所有状态为“等待入住”且入住日期小于当前日期的记录状态为"已超期"
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredReservationRecordsStatus() {
        logger.info("开始执行更新过期预约记录的任务...");
        UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("status", "等待入住").lt("checkin_date", LocalDate.now()).set("status", "已超期");
        int updatedRows = reservationRecordMapper.update(null, updateWrapper);
        logger.info("更新了 {} 条过期的预约记录！", updatedRows);
    }
}
