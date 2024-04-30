<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            工资财务信息
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>员工编号</th>
                                    <th>基本工资</th>
                                    <th>额外工资</th>
                                    <th>总工资</th>
                                    <th>发放日期</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="record in records" :key="record.number">
                                    <td>{{ record.staffNumber }}</td>
                                    <td>{{ record.basicSalary }}</td>
                                    <td>{{ record.extraSalary }}</td>
                                    <td>{{ record.totalSalary }}</td>
                                    <td>{{ record.releaseDate.substring(0, 10) }}</td>
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
                    type: "工资财务",
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