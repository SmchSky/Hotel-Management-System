import $ from 'jquery'

export default {
    namespaced: true,
    state: {
        username: "",
        name: "",
        number: "",
        phone: "",
        token: "",
        duty: "",
        default_working_page: "",
        is_login: false,
    },
    getters: {
    },
    mutations: {
        //若要调用mutations里的函数，用store.commit()
        //用于修改State中的数据的函数，完成同步操作
        updateUser (state, user) {
            state.username = user.username;
            state.name = user.name;
            state.number = user.number;
            state.phone = user.phone;
            state.duty = user.duty;
            state.is_login = user.is_login;
            state.default_working_page = user.default_working_page;
        },
        updateToken (state, token) {
            state.token = token;
        },
        logout (state) {
            state.username = "";
            state.name = "";
            state.number = "";
            state.phone = "";
            state.token = "";
            state.duty = "";
            state.default_working_page = "";
            state.is_login = false;
        },
        updateIsLogin (state, is_login) {
            state.is_login = is_login;
        },
        updateUsername (state, username) {
            state.username = username;
        },
        updateDefaultWorkingPage (state, default_working_page) {
            state.default_working_page = default_working_page;
        },
        updateDuty (state, duty) {
            state.duty = duty;
        },
    },
    actions: {
        //若要调用actions里的函数，用store.dispatch()
        //完成异步操作（需要与云端交互）
        login (context, data) {
            $.ajax({
                url: 'http://localhost:3000/staff_user/account/login/',
                type: 'post',  //向服务器提交数据
                data: {
                    username: data.username,
                    password: data.password,
                    duty: data.duty,
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //将用户信息存入本地存储
                        localStorage.setItem("jwt_token", resp.token);

                        //将default_working_page存入本地存储
                        if (data.duty === "酒店前台工作人员") {
                            localStorage.setItem("default_working_page", "front_desk_staff_default_working_page");
                        } else if (data.duty === "餐厅前台工作人员") {
                            localStorage.setItem("default_working_page", "restaurant_staff_default_working_page");
                        } else if (data.duty === "财务管理员") {
                            localStorage.setItem("default_working_page", "financial_staff_default_working_page");
                        } else if (data.duty === "人事管理员") {
                            localStorage.setItem("default_working_page", "hr_staff_default_working_page");
                        } else if (data.duty === "采购人员") {
                            localStorage.setItem("default_working_page", "purchase_staff_default_working_page");
                        } else if (data.duty === "超级用户") {
                            localStorage.setItem("default_working_page", "superuser_default_working_page");
                        }

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
                url: 'http://localhost:3000/staff_user/account/info/',
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
                            name: resp.name,
                            number: resp.number,
                            phone: resp.phone,
                            duty: data.duty,
                            default_working_page: localStorage.getItem("default_working_page"),
                            is_login: true,
                        });

                        //将用户信息存入本地
                        localStorage.setItem("staff_username", resp.username);
                        localStorage.setItem("staff_duty", data.duty);
                        localStorage.setItem("staff_number", resp.number);
                        localStorage.setItem("staff_name", resp.name);
                        localStorage.setItem("staff_phone", resp.phone);

                        data.success();  //回调函数
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
            //将用户信息从本地存储删除
            localStorage.removeItem("jwt_token");
            localStorage.removeItem("staff_username");
            localStorage.removeItem("staff_duty");
            localStorage.removeItem("staff_number");
            localStorage.removeItem("staff_name");
            localStorage.removeItem("staff_phone");
            localStorage.removeItem("default_working_page");

            //调用mutations中的函数
            context.commit("logout");
        },


        //酒店前台工作人员办理退房时获取顾客的订单列表的函数
        getLiveRecordList (context, data) {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/get_live_record_list/",
                type: "post",
                data: {
                    resident_phone: data.resident_phone,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        data.success(resp.live_record_list);
                    } else {
                        data.error(resp.error_message);
                    }
                }
            })
        },

        //酒店前台工作人员点击退房调用的函数
        checkout (context, data) {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/check_out/",
                type: "post",
                data: {
                    number: data.number,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        data.success();
                    }
                }
            });
        },

        //酒店前台工作人员查询预约记录的函数
        getReservationRecordList (context, data) {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/get_reservation_record_list/",
                type: "post",
                data: {
                    resident_phone: data.resident_phone,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        data.success(resp.reservation_record_list);
                    } else {
                        data.error(resp.error_message);
                    }
                }
            });
        },

        //酒店前台工作人员办理已预约入住调用的函数
        checkin (context, data) {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/check_in/",
                type: "post",
                data: {
                    number: data.number,
                    id: data.resident_id,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        data.success();
                    } else {
                        data.error(resp.error_message);
                    }
                }
            });
        },

    },
    modules: {
    }
}