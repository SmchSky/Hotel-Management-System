<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <!-- 给字体添加样式要借助span标签！ -->
                        <span style="font-size:140%;">
                            个人账户信息
                        </span>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>信息名</th>
                                    <th>信息值</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 第一行，username信息 -->
                                <tr>
                                    <td>用户名</td>
                                    <td>{{ staff_info_origin.username }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-staff-username-modal"
                                            @click="clear_staff_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-staff-username-modal" tabindex="-1">
                                            <div class="modal-dialog modal-m modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改用户名
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-username" class="form-label">新的用户名</label>
                                                            <input v-model="staff_info_update.username" type="text"
                                                                class="form-control" id="update-username">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ staff_info_update.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_staff_username">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 第二行，password信息 -->
                                <tr>
                                    <td>密码</td>
                                    <td>********
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-staff-password-modal"
                                            @click="clear_staff_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-staff-password-modal" tabindex="-1">
                                            <div class="modal-dialog modal-m modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改密码
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-password" class="form-label">新的密码</label>
                                                            <input v-model="staff_info_update.password" type="password"
                                                                class="form-control" id="update-password">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-password_confirmed"
                                                                class="form-label">确认密码</label>
                                                            <input v-model="staff_info_update.confirmed_password"
                                                                type="password" class="form-control"
                                                                id="update-password_confirmed">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ staff_info_update.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_staff_password">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 第三行，number信息 -->
                                <tr>
                                    <td>员工编号</td>
                                    <td>{{ staff_info_origin.number }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger not_allowed">修改</button>
                                    </td>
                                </tr>
                                <!-- 第四行，name信息 -->
                                <tr>
                                    <td>姓名</td>
                                    <td>{{ staff_info_origin.name }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-staff-name-modal"
                                            @click="clear_staff_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-staff-name-modal" tabindex="-1">
                                            <div class="modal-dialog modal-m modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改姓名
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-name" class="form-label">新的姓名</label>
                                                            <input v-model="staff_info_update.name" type="text"
                                                                class="form-control" id="update-name">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ staff_info_update.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_staff_name">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 第五行，phone信息 -->
                                <tr>
                                    <td>手机号</td>
                                    <td>{{ staff_info_origin.phone }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-staff-phone-modal"
                                            @click="clear_staff_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-staff-phone-modal" tabindex="-1">
                                            <div class="modal-dialog modal-m modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改手机号
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-phone" class="form-label">新的手机号</label>
                                                            <input v-model="staff_info_update.phone" type="text"
                                                                class="form-control" id="update-phone">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ staff_info_update.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_staff_phone">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 第六行，duty信息 -->
                                <tr>
                                    <td>职务</td>
                                    <td>{{ staff_info_origin.duty }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger not_allowed">修改</button>
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
import { reactive } from 'vue';
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';
import { useStore } from 'vuex'

export default {
    setup () {
        const store = useStore();

        //利用reactive定义staff_info_origin全局变量
        const staff_info_origin = reactive({
            username: "",
            number: "",
            name: "",
            phone: "",
            duty: "",
        });

        //利用reactive定义staff_info_update全局变量
        const staff_info_update = reactive({
            username: "",
            password: "",
            confirmed_password: "",
            name: "",
            phone: "",
            error_message: "",
        });

        //为staff_info_origin变量赋值
        const refresh_staff_info_origin = () => {
            staff_info_origin.username = localStorage.getItem("staff_username");
            staff_info_origin.number = localStorage.getItem("staff_number");
            staff_info_origin.name = localStorage.getItem("staff_name");
            staff_info_origin.phone = localStorage.getItem("staff_phone");
            staff_info_origin.duty = localStorage.getItem("staff_duty");
        }

        //清空staff_info_update变量
        const clear_staff_info_update = () => {
            staff_info_update.username = "";
            staff_info_update.password = "";
            staff_info_update.confirmed_password = "";
            staff_info_update.name = "";
            staff_info_update.phone = "";
            staff_info_update.error_message = "";
        }

        //在加载页面时就执行的函数
        refresh_staff_info_origin();

        //更新username的函数
        const update_staff_username = () => {
            //清空报错信息
            staff_info_update.error_message = "";
            $.ajax({
                url: "http://localhost:3000/staff_user/update_info/",
                type: "post",
                data: {
                    type: "username",
                    duty: staff_info_origin.duty,
                    number: staff_info_origin.number,
                    info: staff_info_update.username,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //将新的token存入本地
                        localStorage.setItem("jwt_token", resp.token);
                        //数据库更新成功
                        store.dispatch("staff_user/getInfo", {
                            token: resp.token,
                            duty: localStorage.getItem("staff_duty"),
                            success () {
                                //获取新的用户信息成功
                                staff_info_update.error_message = "修改成功！";
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-staff-username-modal').hide();
                                    //刷新用户信息
                                    refresh_staff_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })
                    } else {
                        //将错误信息显示出来
                        staff_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        //更新password的函数
        const update_staff_password = () => {
            //清空报错信息
            staff_info_update.error_message = "";
            $.ajax({
                url: "http://localhost:3000/staff_user/update_info/",
                type: "post",
                data: {
                    type: "password",
                    duty: staff_info_origin.duty,
                    number: staff_info_origin.number,
                    info: staff_info_update.password,
                    info_confirmed: staff_info_update.confirmed_password,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //更新成功
                        staff_info_update.error_message = "修改成功！";
                        store.dispatch("staff_user/getInfo", {
                            duty: localStorage.getItem("staff_duty"),
                            success () {
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-staff-password-modal').hide();
                                    //刷新用户信息
                                    refresh_staff_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })

                    } else {
                        //否则将错误信息显示出来
                        staff_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        //更新name的函数
        const update_staff_name = () => {

            //清空报错信息
            staff_info_update.error_message = "";

            $.ajax({
                url: "http://localhost:3000/staff_user/update_info/",
                type: "post",
                data: {
                    type: "name",
                    duty: staff_info_origin.duty,
                    number: staff_info_origin.number,
                    info: staff_info_update.name,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //更新成功
                        staff_info_update.error_message = "修改成功！";
                        store.dispatch("staff_user/getInfo", {
                            duty: localStorage.getItem("staff_duty"),
                            success () {
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-staff-name-modal').hide();
                                    //刷新用户信息
                                    refresh_staff_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })

                    } else {
                        //否则将错误信息显示出来
                        staff_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        //更新phone的函数
        const update_staff_phone = () => {

            //清空报错信息
            staff_info_update.error_message = "";

            $.ajax({
                url: "http://localhost:3000/staff_user/update_info/",
                type: "post",
                data: {
                    type: "phone",
                    duty: staff_info_origin.duty,
                    number: staff_info_origin.number,
                    info: staff_info_update.phone,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //更新成功
                        staff_info_update.error_message = "修改成功！";
                        store.dispatch("staff_user/getInfo", {
                            duty: localStorage.getItem("staff_duty"),
                            success () {
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-staff-phone-modal').hide();
                                    //刷新用户信息
                                    refresh_staff_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })

                    } else {
                        //否则将错误信息显示出来
                        staff_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            staff_info_origin,
            staff_info_update,
            update_staff_username,
            update_staff_password,
            update_staff_name,
            update_staff_phone,
            clear_staff_info_update,
        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
}

.not_allowed {
    cursor: not-allowed;
}
</style>