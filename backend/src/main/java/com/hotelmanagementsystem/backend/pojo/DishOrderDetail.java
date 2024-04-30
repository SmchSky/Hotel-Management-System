package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishOrderDetail {
    @TableId
    private String number;
    private String dishNumber;
    private Integer unitPrice;
    private Integer quantity;
}
