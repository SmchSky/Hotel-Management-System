<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            预约记录
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>预约编号</th>
                                    <th>预约时间</th>
                                    <th>房间类型</th>
                                    <th>总价格</th>
                                    <th>入住时间段</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in reservation_records" :key="record.number">
                                    <td>{{ record.number }}</td>
                                    <td>{{ record.time }}</td>
                                    <td>{{ record.roomType }}</td>
                                    <td>{{ record.price }}</td>
                                    <td>{{ record.checkinDate }} 至 {{ record.latestLeaveDate }}</td>
                                    <td>{{ record.status }}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" data-bs-toggle="modal"
                                            :data-bs-target="'#more-info-modal' + record.number"
                                            style="margin-right:10px;">更多信息</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'more-info-modal' + record.number" tabindex="-1">
                                            <!-- modal-sm是让模态框变小 -->
                                            <div class="modal-dialog modal-l modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            更多信息
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label class="form-label">住客姓名：</label>
                                                            <span>{{ record.residentName }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">住客手机号：</label>
                                                            <span>{{ record.residentPhone }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">客房编号：</label>
                                                            <span>{{ record.roomNumber }}</span>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-success"
                                                            data-bs-dismiss="modal">确定</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#cancel-modal' + record.number"
                                            v-if="record.status === '等待入住'">取消预约</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'cancel-modal' + record.number" tabindex="-1">
                                            <!-- modal-sm是让模态框变小 -->
                                            <div class="modal-dialog modal-sm modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            提示
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        确定取消该预约吗？
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-danger"
                                                            @click="cancel(record)">确定</button>
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
import { ref } from 'vue';
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    setup () {
        //要展示的reservation_records列表
        let reservation_records = ref([]);
        //加载reservation_records列表的函数
        const refresh_reservation_records = () => {
            $.ajax({
                url: "http://localhost:3000/online_user/get_reservation_records/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    reservation_records.value = resp;
                }
            })
        }
        //在加载页面时就执行的函数
        refresh_reservation_records();

        //取消预约的函数
        const cancel = (record) => {
            $.ajax({
                url: "http://localhost:3000/online_user/cancel_reservation/",
                type: "post",
                data: {
                    number: record.number,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //取消预约成功，将模态框关闭
                        Modal.getInstance("#cancel-modal" + record.number).hide();
                        //刷新列表
                        refresh_reservation_records();
                    }
                }
            })
        }

        return {
            reservation_records,
            cancel,
        }
    }
}
</script>

<style scoped></style>