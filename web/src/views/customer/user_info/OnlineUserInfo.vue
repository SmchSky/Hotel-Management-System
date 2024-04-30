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
                                    <td>{{ onlineuser_info_origin.username }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-onlineuser-username-modal"
                                            @click="clear_onlineuser_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-onlineuser-username-modal" tabindex="-1">
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
                                                            <input v-model="onlineuser_info_update.username" type="text"
                                                                class="form-control" id="update-username">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ onlineuser_info_update.error_message
                                                        }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_onlineuser_username">修改</button>
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
                                            data-bs-target="#update-onlineuser-password-modal"
                                            @click="clear_onlineuser_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-onlineuser-password-modal" tabindex="-1">
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
                                                            <input v-model="onlineuser_info_update.password" type="password"
                                                                class="form-control" id="update-password">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-password_confirmed"
                                                                class="form-label">确认密码</label>
                                                            <input v-model="onlineuser_info_update.confirmed_password"
                                                                type="password" class="form-control"
                                                                id="update-password_confirmed">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ onlineuser_info_update.error_message
                                                        }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_onlineuser_password">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 第三行，phone信息 -->
                                <tr>
                                    <td>手机号</td>
                                    <td>{{ onlineuser_info_origin.phone }}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            data-bs-target="#update-onlineuser-phone-modal"
                                            @click="clear_onlineuser_info_update">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" id="update-onlineuser-phone-modal" tabindex="-1">
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
                                                            <input v-model="onlineuser_info_update.phone" type="text"
                                                                class="form-control" id="update-phone">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">
                                                            {{ onlineuser_info_update.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_onlineuser_phone">修改</button>
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
import { reactive } from 'vue';
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';
import { useStore } from 'vuex'

export default {
    setup () {
        const store = useStore();

        //利用reactive定义onlineuser_info_origin全局变量
        const onlineuser_info_origin = reactive({
            username: "",
            phone: "",
        });

        //利用reactive定义onlineuer_info_update全局变量
        const onlineuser_info_update = reactive({
            username: "",
            password: "",
            confirmed_password: "",
            phone: "",
            error_message: "",
        });

        //为onlineuer_info_origin变量赋值
        const refresh_onlineuser_info_origin = () => {
            onlineuser_info_origin.username = localStorage.getItem("onlineuser_username");
            onlineuser_info_origin.phone = localStorage.getItem("onlineuser_phone");
        }

        //清空onlineuser_info_update变量
        const clear_onlineuser_info_update = () => {
            onlineuser_info_update.username = "";
            onlineuser_info_update.password = "";
            onlineuser_info_update.confirmed_password = "";
            onlineuser_info_update.phone = "";
            onlineuser_info_update.error_message = "";
        }

        //在加载页面时就执行的函数
        refresh_onlineuser_info_origin();

        //更新username的函数
        const update_onlineuser_username = () => {
            //清空报错信息
            onlineuser_info_update.error_message = "";
            $.ajax({
                url: "http://localhost:3000/onlineuser_user/update_info/",
                type: "post",
                data: {
                    type: "username",
                    info: onlineuser_info_update.username,
                    origin_username: onlineuser_info_origin.username,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {  //数据库更新成功
                        //将新的token存入本地
                        localStorage.setItem("jwt_token", resp.token);
                        store.dispatch("online_user/getInfo", {
                            success () {
                                //获取新的用户信息成功
                                onlineuser_info_update.error_message = "修改成功！";
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-onlineuser-username-modal').hide();
                                    //刷新用户信息
                                    refresh_onlineuser_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })
                    } else {
                        //将错误信息显示出来
                        onlineuser_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        //更新password的函数
        const update_onlineuser_password = () => {
            //清空报错信息
            onlineuser_info_update.error_message = "";
            $.ajax({
                url: "http://localhost:3000/onlineuser_user/update_info/",
                type: "post",
                data: {
                    type: "password",
                    origin_username: onlineuser_info_origin.username,
                    info: onlineuser_info_update.password,
                    info_confirmed: onlineuser_info_update.confirmed_password,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {  //数据库更新成功
                        store.dispatch("online_user/getInfo", {
                            success () {
                                //获取新的用户信息成功
                                onlineuser_info_update.error_message = "修改成功！";
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-onlineuser-password-modal').hide();
                                    //刷新用户信息
                                    refresh_onlineuser_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })
                    } else {
                        //将错误信息显示出来
                        onlineuser_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        //更新phone的函数
        const update_onlineuser_phone = () => {
            //清空报错信息
            onlineuser_info_update.error_message = "";
            $.ajax({
                url: "http://localhost:3000/onlineuser_user/update_info/",
                type: "post",
                data: {
                    type: "phone",
                    origin_username: onlineuser_info_origin.username,
                    info: onlineuser_info_update.phone,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {  //数据库更新成功
                        store.dispatch("online_user/getInfo", {
                            success () {
                                //获取新的用户信息成功
                                onlineuser_info_update.error_message = "修改成功！";
                                setTimeout(() => {
                                    //关闭模态框
                                    Modal.getInstance('#update-onlineuser-phone-modal').hide();
                                    //刷新用户信息
                                    refresh_onlineuser_info_origin();
                                }, 1000);
                            },
                            error () {
                            }
                        })
                    } else {
                        //将错误信息显示出来
                        onlineuser_info_update.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            onlineuser_info_origin,
            onlineuser_info_update,
            update_onlineuser_username,
            update_onlineuser_password,
            update_onlineuser_phone,
            clear_onlineuser_info_update,
        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
}
</style>