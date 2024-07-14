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
public class ReservationRecord {
    @TableId
    private String number;
    private LocalDateTime time;
    private String username;
    private String userPhone;
    private String residentName;
    private String residentPhone;
    private String roomNumber;
    private String roomType;  //包含“大床房”、“双床房”
    private Integer price;
    private LocalDate checkinDate;
    private LocalDate latestLeaveDate;
    private String status;  //包含“等待入住”、“已完成”、“已取消”
}
