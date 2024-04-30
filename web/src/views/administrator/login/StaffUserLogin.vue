<template>
    <ContentField class="col-5">
        <div class="row justify-content-md-center">
            <div class="col-7">
                <div class="text-center">
                    <label id="title">工作人员用户登录</label>
                </div>
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password">
                    </div>
                    <div>
                        <label for="duty" class="form-label">职务</label>
                        <br>
                        <select name="duty" id="duty">
                            <option value="酒店前台工作人员">酒店前台工作人员</option>
                            <option value="餐厅前台工作人员">餐厅前台工作人员</option>
                            <option value="财务管理员">财务管理员</option>
                            <option value="人事管理员">人事管理员</option>
                            <option value="采购人员">采购人员</option>
                            <option value="超级用户">超级用户</option>
                        </select>
                    </div>
                    <br>
                    <div class="error_message text-center">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary" style="margin:13px 0 10px 0;">登录</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router/index'

export default {
    components: {
        ContentField,
    },
    setup () {
        const store = useStore();
        let username = ref('');  //与上面的username对应
        let password = ref('');
        let error_message = ref('');

        //查看本地存储中是否存在jwt_token，不存在则jwt_token被赋值为null
        const jwt_token = localStorage.getItem("jwt_token");
        //本地存储中存在jwt_token
        if (jwt_token) {
            //更新token
            store.commit("staff_user/updateToken", jwt_token);
            //验证token是否过期
            store.dispatch("staff_user/getInfo", {
                duty: store.state.staff_user.duty,
                success () {  //未过期
                    router.push({ name: "home" });  //跳转到首页
                },
                error () {  //过期

                }
            })
        }

        //触发函数
        const login = () => {
            //清空error_message中的信息
            error_message.value = "";

            store.dispatch("staff_user/login", {
                username: username.value,
                password: password.value,
                duty: document.getElementById("duty").value,

                success () {  //登录成功
                    error_message.value = "登录成功！";
                    store.dispatch("staff_user/getInfo", {
                        duty: document.getElementById("duty").value,
                        success () {
                            setTimeout(() => {
                                router.push({ name: localStorage.getItem("default_working_page") });
                            }, 1000);
                        },
                        error () {
                        }
                    })
                },
                error () {
                    error_message.value = "用户名或密码错误！";
                }

            })//调用actions里面的函数
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    },
}
</script>
<style scoped>
button {
    display: block;
    width: 100%;
    text-align: center;
    margin: 0 auto;
}

.error_message {
    color: red;
    font-size: 100%;
}

#title {
    font-size: 20px;
    padding-bottom: 10px;
}
</style>