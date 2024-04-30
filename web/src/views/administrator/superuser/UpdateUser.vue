<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <!-- 给字体添加样式要借助span标签！ -->
                        <span style="font-size:140%;">
                            员工账户信息
                        </span>
                        <!-- float-end类是让组件向右对齐的意思！！！ -->
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-user-modal" @click="refresh_useradd">
                            新建员工账户
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="add-user-modal" tabindex="-1">
                            <!-- modal-xl是让模态框变大 -->
                            <div class="modal-dialog modal-l">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">
                                            新建员工账户
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-user-username" class="form-label">用户名</label>
                                            <!-- v-model用于输入与变量的绑定！ -->
                                            <input v-model="useradd.username" type="text" class="form-control"
                                                id="add-user-username">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-user-password" class="form-label">密码</label>
                                            <input v-model="useradd.password" type="text" class="form-control"
                                                id="add-user-password">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-user-name" class="form-label">员工姓名</label>
                                            <input v-model="useradd.name" type="text" class="form-control"
                                                id="add-user-name">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-user-phone" class="form-label">员工手机号</label>
                                            <input v-model="useradd.phone" type="tel" class="form-control"
                                                id="add-user-phone">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-user-duty" class="form-label">员工职务</label>
                                            <br>
                                            <select name="duty" id="duty">
                                                <option value="酒店前台工作人员">酒店前台工作人员</option>
                                                <option value="餐厅前台工作人员">餐厅前台工作人员</option>
                                                <option value="财务管理员">财务管理员</option>
                                                <option value="人事管理员">人事管理员</option>
                                                <option value="采购人员">采购人员</option>
                                            </select>
                                            <!-- <input v-model="useradd.duty" type="text" class="form-control"
                                                id="add-user-number"> -->
                                        </div>
                                    </div>
                                    <div class="modal-footer text-center">
                                        <div class="error_message">{{ useradd.error_message }}</div>
                                        <!-- @click后面加函数名！表示当点击该按钮时调用该函数！ -->
                                        <button type="button" class="btn btn-primary" @click="add_user">新建</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">放弃</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>用户名</th>
                                    <th>职务</th>
                                    <th>员工编号</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="user in staff_users" :key="user.number">
                                    <td>{{ user.username }}</td>
                                    <td>{{ user.duty }}</td>
                                    <td>{{ user.number }}</td>
                                    <td>{{ user.name }}</td>
                                    <td>{{ user.phone }}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#remove-user-modal' + user.number">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'remove-user-modal' + user.number" tabindex="-1">
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
                                                        是否删除该用户？
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-danger"
                                                            @click="remove_user(user)">删除</button>
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
        let staff_users = ref([]);

        //刷新staff_user列表的函数
        const refresh_users = () => {
            $.ajax({
                url: "http://localhost:3000/superuser/get_staff_user_list/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    staff_users.value = resp;
                }
            })
        }

        //在加载页面时就执行的函数
        refresh_users();

        //清空useradd模态框全局变量的函数
        const refresh_useradd = () => {
            useradd.username = "";
            useradd.password = "";
            useradd.name = "";
            useradd.phone = "";
            useradd.duty = "";
            useradd.error_message = "";
        }

        //利用reactive定义useradd全局变量
        const useradd = reactive({
            username: "",
            password: "",
            name: "",
            phone: "",
            duty: "",
            error_message: "",
        });

        //新建user函数
        const add_user = () => {
            //清空报错信息
            useradd.error_message = "";
            $.ajax({
                url: "http://localhost:3000/superuser/add_staff_user/",
                type: "post",
                data: {
                    username: useradd.username,
                    password: useradd.password,
                    name: useradd.name,
                    phone: useradd.phone,
                    duty: document.getElementById("duty").value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        useradd.error_message = "新建用户成功！";
                        setTimeout(() => {
                            //新建user成功，清空变量的值，并将模态框关闭
                            refresh_useradd;
                            Modal.getInstance("#add-user-modal").hide();

                            //刷新一下users列表
                            refresh_users();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        useradd.error_message = resp.error_message;
                    }
                }
            })
        }

        //删除user函数
        const remove_user = (user) => {

            $.ajax({
                url: "http://localhost:3000/superuser/remove_staff_user/",
                type: "post",
                data: {
                    username: user.username,
                    duty: user.duty,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //删除user成功
                        Modal.getInstance("#remove-user-modal" + user.number).hide();
                        //刷新一下users列表
                        refresh_users();
                    }
                }
            })
        }

        return {
            staff_users,
            useradd,
            add_user,
            remove_user,
            refresh_useradd,
        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
}
</style>