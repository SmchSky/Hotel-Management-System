<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            登记员工工资信息
                        </span>
                        <span style="font-size: 100%; margin-left:200px;">员工编号</span>
                        <div class="input-wrapper">
                            <input v-model="number" type="text" class="form-control">
                        </div>
                        <button type="button" class="btn btn-success" @click="query_staff">
                            查询员工
                        </button>
                        <span class="error_message text-center" style="font-size: 120%; display:block;margin:10px auto;">{{
                            query_error_message }}</span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>员工编号</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>职务</th>
                                    <th>基本工资</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="staff in staffs" :key="staff.number">
                                    <td>{{ staff.number }}</td>
                                    <td>{{ staff.name }}</td>
                                    <td>{{ staff.phone }}</td>
                                    <td>{{ staff.duty }}</td>
                                    <td>{{ staff.basicSalary }}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger not_allowed"
                                            v-if="staff.basicSalary === null">登记工资</button>
                                        <button v-else type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            :data-bs-target="'#register-salary-modal' + staff.number"
                                            style="margin-right:15px;" @click="refresh_register_error_message">登记工资</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'register-salary-modal' + staff.number" tabindex="-1">
                                            <!-- modal-sm是让模态框变小 -->
                                            <div class="modal-dialog modal-l modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            登记工资信息
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label class="form-label">基本工资：</label>
                                                            <span>{{ staff.basicSalary }} 元</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">额外工资：</label>
                                                            <input v-model="extra_salary" type="text" class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">实发总工资：</label>
                                                            <span>{{ parseInt(staff.basicSalary) + parseInt(extra_salary)
                                                            }} 元</span>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ register_error_message }}</div>
                                                        <button type="button" class="btn btn-success"
                                                            @click="register_salary(staff)">确定</button>
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
        let staffs = ref([]);
        let query_error_message = ref('');
        let number = ref('');
        let extra_salary = ref('0');
        let register_error_message = ref('');

        //查询staff的函数
        const query_staff = () => {
            query_error_message.value = "";
            $.ajax({
                url: "http://localhost:3000/financial_staff/query_staff/",
                type: "get",
                data: {
                    number: number.value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //取出信息
                        staffs.value = resp.staffs;
                    } else {
                        //否则将错误信息显示出来
                        query_error_message.value = resp.error_message;
                    }
                }
            })
        }
        query_staff();

        const refresh_register_error_message = () => {
            register_error_message.value = "";
        }

        //登记工资信息
        const register_salary = (staff) => {
            $.ajax({
                url: "http://localhost:3000/financial_staff/register_salary/",
                type: "post",
                data: {
                    number: staff.number,
                    basic_salary: staff.basicSalary,
                    extra_salary: extra_salary.value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //清空信息
                        extra_salary.value = 0;
                        register_error_message.value = "登记成功！";
                        setTimeout(() => {
                            Modal.getInstance("#register-salary-modal" + staff.number).hide();
                            location.reload();
                        }, 1500);
                    }
                    else {
                        register_error_message.value = resp.error_message;
                    }
                }
            })
        }

        return {
            staffs,
            number,
            query_error_message,
            extra_salary,
            register_error_message,
            query_staff,
            register_salary,
            refresh_register_error_message,
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

.not_allowed {
    cursor: not-allowed;
}
</style>