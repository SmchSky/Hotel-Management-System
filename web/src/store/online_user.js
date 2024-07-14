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
        // 若要调用actions里的函数，用store.dispatch()方法，完成的是异步操作
        login (context, data) {
            $.ajax({
                url: 'http://localhost:3000/hotel/online_user/login/',
                type: 'post',  //向服务器提交数据
                data: {
                    username: data.username,
                    password: data.password,
                },
                success (resp) {
                    if (resp.message === "success") {
                        // 将token存入本地存储
                        localStorage.setItem("jwt", resp.token);
                        // 保存token以及登录状态到应用程序上下文中
                        context.commit("updateToken", resp.token);
                        context.commit("updateIsLogin", true);
                        // 认证成功的回调函数
                        data.success();
                    } else {
                        // 认证失败的回调函数
                        data.error();
                    }
                }
            });
        },
        getInfo (context, data) {
            $.ajax({
                url: 'http://localhost:3000/hotel/online_user/info/',
                type: 'get',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.message === "success") {
                        context.commit("updateUser", {
                            username: resp.username,
                            phone: resp.phone,
                        });
                        // 将用户信息存入本地
                        localStorage.setItem("onlineuser_username", resp.username);
                        localStorage.setItem("onlineuser_phone", resp.phone);
                        data.success();
                    }
                },
                error (xhr) {
                    // 处理401错误响应
                    if (xhr.status === 401) {
                        let message;
                        try {
                            // 尝试解析响应体中的message字段（该字段在后端的jwt过滤器中设置）
                            const responseJson = JSON.parse(xhr.responseText);
                            message = responseJson.message;
                            alert('Unauthorized access, please log in again! Error message:' + message);
                        } catch (e) {
                            // 如果解析失败，使用默认消息
                            alert('Unauthorized access, please log in again!');
                        }
                    } else {
                        // 处理其他错误
                        alert('An unknown error occurred: ' + xhr.status);
                    }
                }
            });
        },
        logout (context) {
            localStorage.removeItem("jwt");
            localStorage.removeItem("onlineuser_username");
            localStorage.removeItem("onlineuser_phone");
            context.commit("logout");
        }
    },
    modules: {
    }
}