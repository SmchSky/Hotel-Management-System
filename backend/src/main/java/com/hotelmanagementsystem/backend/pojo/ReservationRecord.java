package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRecord {
    @TableId
    private String number;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private java.util.Date time;
    private String username;
    private String userPhone;
    private String residentName;
    private String residentPhone;
    private String roomNumber;
    private String roomType;  //包含“大床房”、“双床房”
    private Integer price;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date checkinDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date latestLeaveDate;
    private String status;  //包含“等待入住”、“已完成”、“已取消”
}
