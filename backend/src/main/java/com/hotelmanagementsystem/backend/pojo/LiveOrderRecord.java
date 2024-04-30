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
public class LiveOrderRecord {
    @TableId
    private String number;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private java.util.Date finishTime;
    private Integer price;
    private String reserveNumber;
    private String residentName;
    private String residentId;
    private String phone;
    private String roomNumber;
    private String roomType;  //包含“大床房”、“双床房”
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date checkinDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date latestLeaveDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date checkoutDate;
    private String status;  //包含“已入住”、“已完成”
}
