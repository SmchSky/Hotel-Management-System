package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveOrderRecord {
    @TableId
    private String number;
    private LocalDateTime finishTime;
    private Integer price;
    private String reserveNumber;
    private String residentName;
    private String residentId;
    private String phone;
    private String roomNumber;
    private String roomType;  //包含“大床房”、“双床房”
    private LocalDate checkinDate;
    private LocalDate latestLeaveDate;
    private LocalDate checkoutDate;
    private String status;  //包含“已入住”、“已完成”
}
