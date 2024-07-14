package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralFinanceRecord {
    @TableId
    private String number;
    private String type;  //包含“餐饮订单”、“住宿订单”
    private Integer price;
    private LocalDateTime finishTime;
}
