<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header" style="text-align:center;">
                        <span style="font-size: 130%; margin-left:70px">入住日期</span>
                        <div class="input-wrapper">
                            <input v-model="checkin.checkin_time" type="date" class="form-control" id="checkin_time">
                        </div>
                        <span style="font-size: 130%;">预计离开日期</span>
                        <div class="input-wrapper">
                            <input v-model="checkin.checkout_time" type="date" class="form-control" id="checkout_time">
                        </div>
                        <button type="button" class="btn btn-primary" style="font-size: 110%; margin-left:40px"
                            @click="query_rooms">查询客房</button>
                        <span class="error_message" style="font-size: 130%; display:block;margin:10px auto;">{{
                            query.error_message }}</span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>客房编号</th>
                                    <th>客房类型</th>
                                    <th>客房面积</th>
                                    <th>单日价格</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="room in rooms" :key="room.number">
                                    <td>{{ room.number }}</td>
                                    <td>{{ room.type }}</td>
                                    <td>{{ room.area }} (㎡)</td>
                                    <td>{{ room.price }} 元</td>
                                    <td>
                                        <button type="button" class="btn btn-success" data-bs-toggle="modal"
                                            :data-bs-target="'#checkin-modal' + room.number"
                                            @click="compute_total_price(room)">办理入住</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'checkin-modal' + room.number" tabindex="-1">
                                            <!-- modal-xl是让模态框变大 -->
                                            <div class="modal-dialog modal-l">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            办理入住
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label class="form-label">住客姓名</label>
                                                            <input v-model="checkin.name" type="text" class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">住客身份证号</label>
                                                            <input v-model="checkin.id" type="text" class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">住客手机号</label>
                                                            <input v-model="checkin.phone" type="text" class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-room-type" class="form-label">总价格：</label>
                                                            <span>{{ checkin.total_price }} 元</span>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="error_message">{{ checkin.error_message }}</div>
                                                        <button type="button" class="btn btn-success"
                                                            @click="check_in(room)">办理入住</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery';
import { ref, reactive } from 'vue';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    setup () {
        //要展示的room列表
        let rooms = ref([]);

        //利用reactive定义checkin全局变量
        const checkin = reactive({
            name: "",
            id: "",
            phone: "",
            total_price: "",
            checkin_time: "",
            checkout_time: "",
            error_message: "",
        });

        //计算total_price的函数
        const compute_total_price = (room) => {
            //将字符串转换为JS的Date类型
            var date_in = new Date(checkin.checkin_time);
            var date_out = new Date(checkin.checkout_time);

            //计算毫秒数差值
            var timeDiff = Math.abs(date_out.getTime() - date_in.getTime());

            //将毫秒数差值转换为天数差值
            var daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

            //将总价格赋值给checkin.total_price
            checkin.total_price = daysDiff * room.price;
        }


        //利用reactive定义query全局变量
        const query = reactive({
            error_message: "",
        });

        //查询房间的函数
        const query_rooms = () => {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/get_selected_room_list/",
                type: "get",
                data: {
                    checkin_time: checkin.checkin_time,
                    checkout_time: checkin.checkout_time,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //查询房间成功
                        rooms.value = resp.selected_rooms;
                        query.error_message = "";
                    } else {
                        //否则将错误信息显示出来
                        query.error_message = resp.error_message;
                    }
                }
            })
        }

        //办理入住的函数
        const check_in = (room) => {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/check_in/",
                type: "post",
                data: {
                    name: checkin.name,
                    id: checkin.id,
                    phone: checkin.phone,
                    total_price: checkin.total_price,
                    checkin_time: checkin.checkin_time,
                    latest_leave_time: checkin.checkout_time,
                    room_number: room.number,
                    room_type: room.type,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //入住信息登记成功
                        checkin.error_message = "入住成功！";
                        setTimeout(() => {
                            Modal.getInstance("#checkin-modal" + room.number).hide();
                        }, 1000);
                        //刷新整個頁面
                        location.reload();
                    } else {
                        //否则将错误信息显示出来
                        checkin.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            rooms,
            checkin,
            query,
            check_in,
            query_rooms,
            compute_total_price,

        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
}

.input-wrapper {
    display: inline-block;
    margin-left: 10px;
    margin-right: 30px;
    /* 调整输入框之间的间距 */
}
</style>