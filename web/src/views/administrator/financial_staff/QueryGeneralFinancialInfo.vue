<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            常规财务信息
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>订单类型</th>
                                    <th>订单价格</th>
                                    <th>完成时间</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in records" :key="record.number">
                                    <td>{{ record.number }}</td>
                                    <td>{{ record.type }}</td>
                                    <td>{{ record.price }} 元</td>
                                    <td>{{ record.finishTime }}</td>
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
import { ref } from 'vue';

export default {
    setup () {
        //要展示的信息列表
        let records = ref([]);
        //获取records的函数
        const get_records = () => {
            $.ajax({
                url: "http://localhost:3000/financial_staff/get_records/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                data: {
                    type: "常规财务",
                },
                success (resp) {
                    //查询成功
                    records.value = resp.records;
                }
            })
        }
        get_records();
        return {
            records,
        }
    }
}
</script>

<style scoped></style>