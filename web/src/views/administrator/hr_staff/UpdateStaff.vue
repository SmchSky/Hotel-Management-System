<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:140%;">
                            员工个人信息
                        </span>
                        <span style="font-size: 100%; margin-left:200px;">员工编号</span>
                        <div class="input-wrapper">
                            <input v-model="staff_number" type="text" class="form-control">
                        </div>
                        <!-- float-end类是让组件向右对齐的意思！！！ -->
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-staff-modal" @click="refresh_staffadd">
                            新建员工信息
                        </button>
                        <!-- float-end类是让组件向右对齐的意思！！！ -->
                        <button type="button" class="btn btn-success" @click="query_staff">
                            查询员工信息
                        </button>
                        <span class="error_message text-center"
                            style="font-size: 120%; display:block;margin:10px auto;">{{
                                query_error_message }}</span>
                        <!-- Modal -->
                        <div class="modal fade" id="add-staff-modal" tabindex="-1">
                            <!-- modal-xl是让模态框变大 -->
                            <div class="modal-dialog modal-l">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">
                                            新建员工信息
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class=" form-label">姓名</label>
                                            <input v-model="staffadd.name" type="text" class="form-control">
                                        </div>
                                        <div class=" mb-3">
                                            <label class="form-label">性别</label>
                                            <br>
                                            <select id="sex_add">
                                                <option value="男">男</option>
                                                <option value="女">女</option>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">民族</label>
                                            <input v-model="staffadd.nation" type="text" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">身份证号</label>
                                            <input v-model="staffadd.id" type="text" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">手机号</label>
                                            <input v-model="staffadd.phone" type="text" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">出生日期</label>
                                            <input v-model="staffadd.birthdate" type="date" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">籍贯</label>
                                            <input v-model="staffadd.native_place" type="text" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">学历</label>
                                            <br>
                                            <select name="education_add" id="education_add">
                                                <option value="小学">小学</option>
                                                <option value="初中">初中</option>
                                                <option value="中专">中专</option>
                                                <option value="高中">高中</option>
                                                <option value="专科">专科</option>
                                                <option value="本科">本科</option>
                                                <option value="硕士研究生">硕士研究生</option>
                                                <option value="博士研究生">博士研究生</option>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">家庭住址</label>
                                            <input v-model="staffadd.residential_address" type="text"
                                                class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">职务</label>
                                            <input v-model="staffadd.duty" type="text" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">入职日期</label>
                                            <input v-model="staffadd.entry_date" type="date" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">基本工资</label>
                                            <input v-model="staffadd.basic_salary" type="text" class="form-control"
                                                placeholder="默认单位为元，如: 5000">
                                        </div>
                                    </div>
                                    <div class="modal-footer text-center">
                                        <div class="error_message">{{ staffadd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_staff">新建</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">放弃</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>员工编号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>身份证号</th>
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
                                    <td>{{ staff.sex }}</td>
                                    <td>{{ staff.id }}</td>
                                    <td>{{ staff.phone }}</td>
                                    <td>{{ staff.duty }}</td>
                                    <td>{{ staff.basicSalary }}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" data-bs-toggle="modal"
                                            :data-bs-target="'#more-info-modal' + staff.number"
                                            style="margin-right:15px;">更多信息</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'more-info-modal' + staff.number" tabindex="-1">
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
                                                            <label class="form-label">民族：</label>
                                                            <span>{{ staff.nation }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">出生日期：</label>
                                                            <span>{{ staff.birthdate }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">籍贯：</label>
                                                            <span>{{ staff.nativePlace }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">学历：</label>
                                                            <span>{{ staff.education }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">入职日期：</label>
                                                            <span>{{ staff.entryDate }}</span>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">家庭住址：</label>
                                                            <span>{{ staff.residentialAddress }}</span>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-success"
                                                            data-bs-dismiss="modal">确定</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button style="margin-right:15px;" type="button" class="btn btn-warning"
                                            data-bs-toggle="modal"
                                            :data-bs-target="'#update-staff-modal' + staff.number"
                                            @click="load_staffupdate(staff)">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-staff-modal' + staff.number" tabindex="-1">
                                            <!-- modal-xl是让模态框变大 -->
                                            <div class="modal-dialog modal-l">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改员工信息
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label class=" form-label">姓名</label>
                                                            <input v-model="staffupdate.name" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class=" mb-3">
                                                            <label class="form-label">性别</label>
                                                            <br>
                                                            <select id="sex_update">
                                                                <option value="男">男</option>
                                                                <option value="女">女</option>
                                                            </select>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">民族</label>
                                                            <input v-model="staffupdate.nation" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">身份证号</label>
                                                            <input v-model="staffupdate.id" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">手机号</label>
                                                            <input v-model="staffupdate.phone" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">生日</label>
                                                            <input v-model="staffupdate.birthdate" type="date"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">籍贯</label>
                                                            <input v-model="staffupdate.native_place" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">学历</label>
                                                            <br>
                                                            <select id="education_update">
                                                                <option value="小学">小学</option>
                                                                <option value="初中">初中</option>
                                                                <option value="中专">中专</option>
                                                                <option value="高中">高中</option>
                                                                <option value="专科">专科</option>
                                                                <option value="本科">本科</option>
                                                                <option value="硕士研究生">硕士研究生</option>
                                                                <option value="博士研究生">博士研究生</option>
                                                            </select>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">家庭住址</label>
                                                            <input v-model="staffupdate.residential_address" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">职务</label>
                                                            <input v-model="staffupdate.duty" type="text"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">入职日期</label>
                                                            <input v-model="staffupdate.entry_date" type="date"
                                                                class="form-control">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label class="form-label">基本工资</label>
                                                            <input v-model="staffupdate.basic_salary" type="text"
                                                                class="form-control" placeholder="默认单位为元，如: 5000">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="error_message">{{ staffupdate.error_message }}</div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_staff(staff)">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#remove-staff-modal' + staff.number">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'remove-staff-modal' + staff.number" tabindex="-1">
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
                                                        是否删除该员工？
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-danger"
                                                            @click="remove_staff(staff)">删除</button>
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
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    setup () {
        //要展示的room列表
        let staffs = ref([]);
        let staff_number = ref('');
        let query_error_message = ref('');

        //刷新room列表的函数
        const refresh_staffs = () => {
            $.ajax({
                url: "http://localhost:3000/hr_staff/get_all_staff_list/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    staffs.value = resp.staffs;
                }
            })
        }

        //在加载页面时就执行的函数
        refresh_staffs();

        //利用reactive定义staffdd全局变量
        const staffadd = reactive({
            name: "",
            sex: "",
            phone: "",
            duty: "",
            basic_salary: "",
            nation: "",
            birthdate: "",
            native_place: "",
            entry_date: "",
            residential_address: "",
            error_message: "",
        });

        //清空staffadd全局变量的函数
        const refresh_staffadd = () => {
            staffadd.name = "";
            staffadd.id = "";
            staffadd.phone = "";
            staffadd.duty = "";
            staffadd.basic_salary = "";
            staffadd.nation = "";
            staffadd.birthdate = "";
            staffadd.native_place = "";
            staffadd.entry_date = "";
            staffadd.residential_address = "";
            staffadd.error_message = "";
        }

        //新建staff函数
        const add_staff = () => {
            $.ajax({
                url: "http://localhost:3000/hr_staff/add_staff/",
                type: "post",
                data: {
                    name: staffadd.name,
                    sex: document.getElementById("sex_add").value,
                    id: staffadd.id,
                    phone: staffadd.phone,
                    duty: staffadd.duty,
                    basic_salary: staffadd.basic_salary,
                    nation: staffadd.nation,
                    birthdate: staffadd.birthdate,
                    native_place: staffadd.native_place,
                    entry_date: staffadd.entry_date,
                    education: document.getElementById("education_add").value,
                    residential_address: staffadd.residential_address,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        staffadd.error_message = "新建员工信息成功！";
                        setTimeout(() => {
                            //清空缓存信息
                            refresh_staffadd();
                            Modal.getInstance("#add-staff-modal").hide();
                            //刷新一下staffs列表
                            refresh_staffs();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        staffadd.error_message = resp.error_message;
                    }
                }
            })
        }

        //删除staff函数
        const remove_staff = (staff) => {
            $.ajax({
                url: "http://localhost:3000/hr_staff/remove_staff/",
                type: "post",
                data: {
                    number: staff.number,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //删除成功，将模态框关闭
                        Modal.getInstance("#remove-staff-modal" + staff.number).hide();
                        //刷新一下room列表
                        refresh_staffs();
                    }
                },
            })
        }


        //利用reactive定义staffupdate全局变量
        const staffupdate = reactive({
            name: "",
            sex: "",
            id: "",
            phone: "",
            duty: "",
            basic_salary: "",
            nation: "",
            birthdate: "",
            native_place: "",
            entry_date: "",
            education: "",
            residential_address: "",
            error_message: "",
        });

        //用于将当前选中要更新的satff信息加载到模态框里
        const load_staffupdate = (staff) => {
            staffupdate.error_message = "";
            staffupdate.name = staff.name;
            staffupdate.sex = staff.sex;
            staffupdate.id = staff.id;
            staffupdate.phone = staff.phone;
            staffupdate.duty = staff.duty;
            staffupdate.basic_salary = staff.basicSalary;
            staffupdate.nation = staff.nation;
            staffupdate.birthdate = staff.birthdate;
            staffupdate.native_place = staff.nativePlace;
            staffupdate.entry_date = staff.entryDate;
            staffupdate.education = staff.education;
            staffupdate.residential_address = staff.residentialAddress;
        }

        //更新staff函数
        const update_staff = (staff) => {
            $.ajax({
                url: "http://localhost:3000/hr_staff/update_staff/",
                type: "post",
                data: {
                    number: staff.number,
                    name: staffupdate.name,
                    sex: document.getElementById("sex_update").value,
                    id: staffupdate.id,
                    phone: staffupdate.phone,
                    duty: staffupdate.duty,
                    basic_salary: staffupdate.basic_salary,
                    nation: staffupdate.nation,
                    birthdate: staffupdate.birthdate,
                    native_place: staffupdate.native_place,
                    entry_date: staffupdate.entry_date,
                    education: document.getElementById("education_update").value,
                    residential_address: staffupdate.residential_address,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        staffupdate.error_message = "修改员工信息成功！";
                        setTimeout(() => {
                            //更新room成功，将模态框关闭
                            Modal.getInstance("#update-staff-modal" + staff.number).hide();
                            //刷新一下staffs列表
                            refresh_staffs();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        staffupdate.error_message = resp.error_message;
                    }
                }
            })
        }

        //查询staff的函数
        const query_staff = () => {
            query_error_message.value = "";
            $.ajax({
                url: "http://localhost:3000/hr_staff/get_selected_staff_list/",
                type: "get",
                data: {
                    number: staff_number.value,
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

        return {
            staffs,
            staff_number,
            query_error_message,
            staffadd,
            staffupdate,
            add_staff,
            remove_staff,
            update_staff,
            refresh_staffadd,
            load_staffupdate,
            query_staff,
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