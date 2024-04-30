package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseFinanceRecord {
    @TableId
    private String number;
    private String stuffName;
    private String quantity;
    private String price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date date;  //只包含日期
    private String position;
    private String staffName;
    private String confirmedStaffName;
}
