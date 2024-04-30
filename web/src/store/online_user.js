import $ from 'jquery'

export default {
    namespaced: true,
    state: {
        username: "",
        phone: "",
        token: "",
        is_login: false,
    },
    getters: {
    },
    mutations: {
        //若要调用mutations里的函数，用store.commit()
        //用于修改State中的数据的函数，完成同步操作
        updateUser (state, user) {
            state.username = user.username;
            state.phone = user.phone;
            state.is_login = user.is_login;
        },
        updateToken (state, token) {
            state.token = token;
        },
        logout (state) {
            state.username = "";
            state.phone = "";
            state.token = "";
            state.is_login = false;
        },
        updateIsLogin (state, is_login) {
            state.is_login = is_login;
        },
        updateUsername (state, username) {
            state.username = username;
        },
    },
    actions: {
        //若要调用actions里的函数，用store.dispatch()
        //完成异步操作（需要与云端交互）
        login (context, data) {
            $.ajax({
                url: 'http://localhost:3000/online_user/account/login/',
                type: 'post',  //向服务器提交数据
                data: {
                    username: data.username,
                    password: data.password,
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //将token存入本地存储
                        localStorage.setItem("jwt_token", resp.token);
                        //调用mutations中的函数
                        context.commit("updateToken", resp.token);
                        //回调函数
                        data.success();
                    } else {
                        //回调函数
                        data.error();
                    }
                },
                error () {
                    //回调函数
                    data.error();
                }
            });
        },

        getInfo (context, data) {
            $.ajax({
                url: 'http://localhost:3000/online_user/account/info/',
                type: 'get',  //向服务器提交数据
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //调用mutations中的函数
                        context.commit("updateUser", {
                            //...resp,  //将resp解构出来
                            username: resp.username,
                            phone: resp.phone,
                            is_login: true,
                        });
                        //将用户信息存入本地
                        localStorage.setItem("onlineuser_username", resp.username);
                        localStorage.setItem("onlineuser_phone", resp.phone);
                        //回调函数
                        data.success();
                    } else {
                        data.error();
                    }
                },
                error () {
                    data.error();
                }
            });
        },

        logout (context) {
            //将token从本地存储删除
            localStorage.removeItem("jwt_token");
            localStorage.removeItem("onlineuser_username");
            localStorage.removeItem("onlineuser_phone");

            //调用mutations中的函数
            context.commit("logout");
        }


    },
    modules: {
    }
}