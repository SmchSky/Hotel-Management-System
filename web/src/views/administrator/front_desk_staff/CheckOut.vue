<template>
    <ContentField class="col-4" style="margin-top:20vh;" v-if="info.flag === false">
        <div class="row justify-content-md-center">
            <div style="width:68.3%">
                <div class="text-center">
                    <label id="title" style="font-size:180%;">办理退房</label>
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
                    <button type="button" class="btn btn-success my_button_2" @click="getLiveRecordList">查询</button>
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
                            订单记录
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>房间编号</th>
                                    <th>房间类型</th>
                                    <th>住客姓名</th>
                                    <th>入住时间段</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in live_record_list" :key="record.number">
                                    <td>{{ record.number }}</td>
                                    <td>{{ record.roomNumber }}</td>
                                    <td>{{ record.roomType }}</td>
                                    <td>{{ record.residentName }}</td>
                                    <td>{{ record.checkinDate }} 至 {{ record.latestLeaveDate }}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#checkout-modal' + record.number"
                                            @click="refresh_checkout_error_message">退房</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'checkout-modal' + record.number" tabindex="-1">
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
                                                        确认退房？
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="checkout_error_message">
                                                            {{ info.checkout_error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-danger"
                                                            @click="checkout(record)">确定</button>
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
        ContentField,
    },
    setup () {
        const store = useStore();

        //定义live_record_list
        let live_record_list = ref([]);
        let resident_phone = ref('');

        //利用reactive定义info全局变量
        const info = reactive({
            flag: false,
            query_error_message: "",
            checkout_error_message: "",
        });

        const refresh_checkout_error_message = () => {
            info.checkout_error_message = "";
        }

        //获取订单记录列表
        const getLiveRecordList = () => {
            store.dispatch("staff_user/getLiveRecordList", {
                //住客手机号
                resident_phone: resident_phone.value,
                success (live_record_list_1) {
                    //查询房间成功
                    info.query_error_message = "";
                    //复制live_record_list
                    live_record_list.value = live_record_list_1;
                    //显示查询结果页面
                    info.flag = true;
                },
                error (error_message) {
                    info.flag = false;
                    info.query_error_message = error_message;
                }
            })
        }

        //退房的函数
        const checkout = (record) => {
            store.dispatch("staff_user/checkout", {
                //房间编号
                number: record.number,
                success () {
                    //退房成功
                    info.checkout_error_message = "退房成功！";
                    setTimeout(() => {
                        //将模态框关闭
                        Modal.getInstance("#checkout-modal" + record.number).hide();
                        //再次getlist
                        store.dispatch("staff_user/getLiveRecordList", {
                            resident_phone: resident_phone.value,
                            success (live_record_list_1) {
                                //查询记录成功
                                info.query_error_message = "";
                                //复制live_record_list
                                live_record_list.value = live_record_list_1;
                                //显示查询结果页面
                                info.flag = true;
                            },
                            error () {
                                info.flag = false;
                                info.query_error_message = "";
                                resident_phone.value = "";
                            }
                        })
                    }, 1000);
                },
            })
        }

        return {
            checkout,
            refresh_checkout_error_message,
            getLiveRecordList,
            live_record_list,
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

.checkout_error_message {
    color: red;
    font: 80%;
}

.my_button_2 {
    display: block;
    width: 100%;
    text-align: center;
    margin-bottom: 15px;
}

#title {
    margin: 10px auto;
    font-size: 20px;
    padding-bottom: 10px;
}
</style>