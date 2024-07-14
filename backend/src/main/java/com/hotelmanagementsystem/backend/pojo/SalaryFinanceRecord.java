package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryFinanceRecord {
    @TableId
    private String staffNumber;
    private String basicSalary;
    private String extraSalary;
    private String totalSalary;
    private LocalDate releaseDate;
}
