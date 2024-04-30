<template>
    <ContentField class="col-4" style="margin-top:20vh;" v-if="info.flag === false">
        <div class="row justify-content-md-center">
            <div style="width:68.3%">
                <div class="text-center">
                    <label id="title" style="font-size:180%;">已预约入住</label>
                </div>
                <div class="text-center">
                    <label id="title">住客手机号</label>
                </div>
                <div class="mb-3">
                    <input v-model="resident_phone" type="tel" class="form-control">
                </div>
                <div class="query_error_message  text-center">
                    {{ info.query_error_message }}
                </div>
                <div style="display: flex; justify-content: center;">
                    <button type="button" class="btn btn-success my_button_2" @click="getReservationRecordList">查询</button>
                </div>
            </div>
        </div>
    </ContentField>
    <div class="container" v-if="info.flag === true">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            预约列表
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>住客姓名</th>
                                    <th>房间编号</th>
                                    <th>房间类型</th>
                                    <th>总价格</th>
                                    <th>入住时间段</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in reservation_record_list" :key="record.number">
                                    <td>{{ record.residentName }}</td>
                                    <td>{{ record.roomNumber }}</td>
                                    <td>{{ record.roomType }}</td>
                                    <td>{{ record.price }}</td>
                                    <td>{{ record.checkinDate }} 至 {{ record.latestLeaveDate }}</td>
                                    <td>
                                        <button type="button" class="btn btn-success" data-bs-toggle="modal"
                                            :data-bs-target="'#checkin-modal' + record.number"
                                            @click="refresh_checkin_error_message">办理入住</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'checkin-modal' + record.number" tabindex="-1">
                                            <!-- modal-sm是让模态框变小 -->
                                            <div class="modal-dialog modal-l modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            办理入住
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="modal-body">
                                                            <div class="mb-3">
                                                                <label class="form-label">住客身份证号</label>
                                                                <input v-model="info.resident_id" type="text"
                                                                    class="form-control">
                                                            </div>
                                                            <div class="mb-3">
                                                                <label class="form-label">总价格：</label>
                                                                <span>{{ record.price }} 元</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="checkin_error_message">
                                                            {{ info.checkin_error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-success"
                                                            @click="checkin(record)">确定</button>
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
import { ref, reactive } from 'vue';
import ContentField from '@/components/ContentField.vue'
import { Modal } from 'bootstrap/dist/js/bootstrap.js';
import { useStore } from 'vuex'

export default {
    components: {
        ContentField
    },
    setup () {
        const store = useStore();

        //定义reservation_record_list
        let reservation_record_list = ref([]);
        let resident_phone = ref('');

        //利用reactive定义info全局变量
        const info = reactive({
            flag: false,
            resident_id: "",
            query_error_message: "",
            checkin_error_message: "",
        });

        const refresh_checkin_error_message = () => {
            info.checkin_error_message = "";
        }

        //获取订单记录列表
        const getReservationRecordList = () => {
            store.dispatch("staff_user/getReservationRecordList", {
                //住客手机号
                resident_phone: resident_phone.value,
                success (reservation_record_list_1) {
                    //查询房间成功
                    info.query_error_message = "";
                    //复制reservation_record_list
                    reservation_record_list.value = reservation_record_list_1;
                    //显示查询结果页面
                    info.flag = true;
                },
                error (error_message) {
                    info.flag = false;
                    info.query_error_message = error_message;
                }
            })
        }

        //办理入住的函数
        const checkin = (record) => {
            store.dispatch("staff_user/checkin", {
                //预约编号
                number: record.number,
                resident_id: info.resident_id,
                success () {
                    //入住成功
                    info.checkin_error_message = "入住成功！";
                    setTimeout(() => {
                        //将模态框关闭
                        Modal.getInstance("#checkin-modal" + record.number).hide();
                        //再次getlist
                        store.dispatch("staff_user/getReservationRecordList", {
                            resident_phone: resident_phone.value,
                            success (reservation_record_list_1) {
                                //查询记录成功
                                info.querys_error_message = "";
                                //复制reservation_record_list
                                reservation_record_list.value = reservation_record_list_1;
                                //显示查询结果页面
                                info.flag = true;
                                info.resident_id = "";
                            },
                            error () {
                                info.flag = false;
                                info.query_error_message = "";
                                resident_phone.value = "";
                            }
                        })
                    }, 1000);
                },
                error (error_message) {
                    info.checkin_error_message = error_message;
                }
            })
        }

        return {
            checkin,
            refresh_checkin_error_message,
            getReservationRecordList,
            reservation_record_list,
            info,
            resident_phone,
        }
    }
}
</script>

<style scoped>
.query_error_message {
    color: red;
    font: 80%;
    margin: 5px auto;
}

.checkin_error_message {
    color: red;
    font: 80%;
}

#title {
    margin: 10px auto;
    font-size: 20px;
    padding-bottom: 10px;
}

.my_button_2 {
    display: block;
    width: 100%;
    text-align: center;
    margin-bottom: 15px;
}
</style>