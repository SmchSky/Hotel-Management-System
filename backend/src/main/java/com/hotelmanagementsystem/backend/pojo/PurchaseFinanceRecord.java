package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseFinanceRecord {
    @TableId
    private String number;
    private String stuffName;
    private String quantity;
    private String price;
    private LocalDate date;  //只包含日期
    private String position;
    private String staffName;
    private String confirmedStaffName;
}
