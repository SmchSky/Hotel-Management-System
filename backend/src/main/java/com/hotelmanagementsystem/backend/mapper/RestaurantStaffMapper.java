package com.hotelmanagementsystem.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelmanagementsystem.backend.pojo.RestaurantStaffUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantStaffMapper extends BaseMapper<RestaurantStaffUser> {
}
