package com.hotelmanagementsystem.backend.service.impl.online_user.reservation;

import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.ReserveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReserveRoomServiceImpl implements ReserveRoomService {
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public Map<String, String> reserve(Map<String, String> data) {
        //用于返回的map
        Map<String, String> map = new HashMap<>();
        //将信息取出来
        String resident_name = data.get("resident_name");
        String resident_phone = data.get("resident_phone");
        String username = data.get("username");
        String user_phone = data.get("userphone");
        String total_price = data.get("total_price");
        String checkin_time = data.get("checkin_time");
        String latest_leave_time = data.get("latest_leave_time");
        String room_number = data.get("room_number");
        String room_type = data.get("room_type");

        //合法性检验
        if (resident_name.length() == 0) {
            map.put("error_message", "住客姓名不可为空！");
            return map;
        }
        if (resident_name.contains(" ")) {
            map.put("error_message", "住客姓名不可以包含空格！");
            return map;
        }
        if (resident_name.length() >= 20) {
            map.put("error_message", "住客姓名过长！");
            return map;
        }
        if (resident_phone.length() != 11) {
            map.put("error_message", "住客手机号格式错误！");
            return map;
        }
        if (checkin_time == null) {
            map.put("error_message", "入住日期不可为空！");
            return map;
        }
        if (latest_leave_time == null) {
            map.put("error_message", "预计离开日期不可为空！");
            return map;
        }

        //将checkin_time和latest_leave_time转换为Date类型
        Date checkin_date = Date.valueOf(checkin_time);
        Date latest_leave_date = Date.valueOf(latest_leave_time);
        if (checkin_date.after(latest_leave_date)) {
            map.put("error_message", "预计离开日期要晚于入住日期！");
            return map;
        }

        //生成现在的时间，以字符串的形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_string = dateFormat.format(new java.util.Date());

        //利用正则表达式去除now_string中的连字符、冒号和空格
        String now_result = now_string.replaceAll("[-: ]", "");

        //尾缀03代表该编号类型为预约编号
        String reserve_number = now_result.concat("03");

        //新建对象并插入表中
        ReservationRecord reservationRecord = new ReservationRecord(reserve_number, new java.util.Date(), username, user_phone, resident_name, resident_phone, room_number, room_type, Integer.parseInt(total_price), checkin_date, latest_leave_date, "等待入住");
        reservationRecordMapper.insert(reservationRecord);

        //返回map
        map.put("error_message", "success");
        return map;
    }
}
