<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            采购财务信息
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>物品名称</th>
                                    <th>数量</th>
                                    <th>总价格</th>
                                    <th>购买日期</th>
                                    <th>购买地点</th>
                                    <th>采购人</th>
                                    <th>批准人</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in records" :key="record.number">
                                    <td>{{ record.number }}</td>
                                    <td>{{ record.stuffName }}</td>
                                    <td>{{ record.quantity }}</td>
                                    <td>{{ record.price }}</td>
                                    <td>{{ record.date.substring(0, 10) }}</td>
                                    <td>{{ record.position }}</td>
                                    <td>{{ record.staffName }}</td>
                                    <td>{{ record.confirmedStaffName }}</td>
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
                    type: "采购财务",
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