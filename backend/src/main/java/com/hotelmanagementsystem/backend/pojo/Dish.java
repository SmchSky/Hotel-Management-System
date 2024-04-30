package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @TableId
    private String number;
    private String name;
    private Integer price;
    private String status;  //包含“有库存”和“无库存”两个状态
}
