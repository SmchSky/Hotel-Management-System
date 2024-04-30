package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private LiveOrderRecordMapper liveOrderRecordMapper;
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public Map<String, String> checkIn(Map<String, String> data) {
        //返回map
        Map<String, String> map = new HashMap<>();
        //获取前端信息
        String number = data.get("number");  //预约编号
        String name = data.get("name");
        String id = data.get("id");
        String total_price = data.get("total_price");
        String checkin_time = data.get("checkin_time");
        String latest_leave_time = data.get("latest_leave_time");
        String room_number = data.get("room_number");
        String room_type = data.get("room_type");
        String phone = data.get("phone");

        //如果是直接入住
        if (number == null) {
            //合法性检验
            if (name.length() == 0) {
                map.put("error_message", "住客姓名不可为空！");
                return map;
            }
            if (name.contains(" ")) {
                map.put("error_message", "住客姓名不可以包含空格！");
                return map;
            }
            if (name.length() >= 20) {
                map.put("error_message", "住客姓名过长！");
                return map;
            }
            if (id.length() != 18) {
                map.put("error_message", "住客身份证号格式错误！");
                return map;
            }
            if (phone.length() != 11) {
                map.put("error_message", "手机号格式错误！");
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

            //判断checkin_date是否在latest_leave_date后面
            if (checkin_date.after(latest_leave_date)) {
                map.put("error_message", "预计离开日期要晚于入住日期！");
                return map;
            }

            //生成现在的时间，以字符串的形式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now_string = dateFormat.format(new java.util.Date());

            //利用正则表达式去除now_string中的连字符、冒号和空格
            String now_result = now_string.replaceAll("[-: ]", "");

            //尾缀01代表该订单编号的类型为住宿订单
            String order_number = now_result.concat("01");

            //新建对象，并在住宿订单表中插入新元组
            LiveOrderRecord liveOrderRecord = new LiveOrderRecord(order_number, new java.util.Date(), Integer.parseInt(total_price), null, name, id, phone, room_number, room_type, checkin_date, latest_leave_date, null, "已入住");
            liveOrderRecordMapper.insert(liveOrderRecord);

            //返回结果
            map.put("error_message", "success");
            return map;
        }


        //如果是预约入住
        //合法性检验
        if (id.length() != 18) {
            map.put("error_message", "住客身份证号格式错误！");
            return map;
        }
        //从预约表中取出对应的元组
        QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        ReservationRecord reservationRecord = reservationRecordMapper.selectOne(queryWrapper);

        //生成现在的时间，以字符串的形式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_string = dateFormat.format(new java.util.Date());
        //利用正则表达式去除now_string中的连字符、冒号和空格
        String now_result = now_string.replaceAll("[-: ]", "");
        //尾缀01代表该订单编号的类型为住宿订单
        String order_number = now_result.concat("01");

        //向住宿订单表中插入新的记录
        LiveOrderRecord liveOrderRecord = new LiveOrderRecord(order_number, new java.util.Date(), reservationRecord.getPrice(), number, reservationRecord.getResidentName(), id, reservationRecord.getResidentPhone(), reservationRecord.getRoomNumber(), reservationRecord.getRoomType(), reservationRecord.getCheckinDate(), reservationRecord.getLatestLeaveDate(), null, "已入住");
        liveOrderRecordMapper.insert(liveOrderRecord);

        //将预约记录的状态改为“已完成”
        UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number).set("status", "已完成");
        reservationRecordMapper.update(null, updateWrapper);

        //返回结果
        map.put("error_message", "success");
        return map;
    }
}