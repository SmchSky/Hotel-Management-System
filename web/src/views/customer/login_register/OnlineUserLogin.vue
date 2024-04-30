<template>
    <ContentField class="col-5">
        <div class="row justify-content-md-center">
            <div class="col-7">
                <div class="text-center">
                    <label id="title">线上用户登录</label>
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
                    <div class="error_message text-center">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary" style="margin-top:13px;">登录</button>
                    <button id="register_button" @click="redirectToRegister" class="btn btn-warning"
                        style="margin-bottom:10px;">注册</button>
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
        //注册按钮跳转函数
        const redirectToRegister = () => {
            // 在此处执行页面跳转逻辑
            router.push({ name: "register" });
        };
        const store = useStore();
        let username = ref('');  //与上面的username对应
        let password = ref('');
        let error_message = ref('');

        //查看本地存储中是否存在jwt_token，不存在则jwt_token被赋值为null
        const jwt_token = localStorage.getItem("jwt_token");
        //本地存储中存在jwt_token
        if (jwt_token) {
            //更新token
            store.commit("online_user/updateToken", jwt_token);

            //验证token是否过期
            store.dispatch("online_user/getInfo", {
                success () {  //未过期
                    router.push({ name: "home" });  //跳转到首页
                },
                error () {  //过期
                }
            })
        }

        //触发函数
        const login = () => {
            error_message.value = "";  //清空error_message中的信息
            store.dispatch("online_user/login", {
                username: username.value,
                password: password.value,
                success () {  //登录成功
                    error_message.value = "登录成功！";
                    store.dispatch("online_user/getInfo", {
                        success () {
                            setTimeout(() => {
                                //登录成功后跳转到的页面名称
                                router.push({ name: "reserve_room" });
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
            redirectToRegister,
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

div.error_message {
    color: red;
}

#title {
    font-size: 20px;
    padding-bottom: 10px;
}

#register_button {
    margin-top: 10px;
}
</style>