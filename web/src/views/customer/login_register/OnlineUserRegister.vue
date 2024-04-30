<template>
    <ContentField class="col-5">
        <div class="row justify-content-md-center">
            <div class="col-7">
                <div class="text-center">
                    <label id="title">线上用户注册</label>
                </div>
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password">
                    </div>
                    <div class="mb-3">
                        <label for="confirmed_password" class="form-label">确认密码</label>
                        <input v-model="confirmed_password" type="password" class="form-control" id="confirmed_password">
                    </div>
                    <div class="mb-4">
                        <label for="phone" class="form-label">手机号</label>
                        <input v-model="phone" type="tel" class="form-control" id="phone">
                    </div>
                    <div class="error_message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">注册</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import { ref } from 'vue'
import router from '@/router/index'
import $ from "jquery"

export default {
    components: {
        ContentField,
    },
    setup () {
        let username = ref('');
        let password = ref('');
        let confirmed_password = ref('');
        let phone = ref('');
        let error_message = ref('');

        const register = () => {
            $.ajax({
                url: 'http://localhost:3000/online_user/account/register/',
                type: 'post',  //要修改数据库用post，不修改数据库用get
                data: {
                    username: username.value,
                    password: password.value,
                    confirmed_password: confirmed_password.value,
                    phone: phone.value,
                },
                success: function (resp) {  //当字典中的值为一个函数时，可以简写成success(resp){}
                    console.log(resp);
                    if (resp.error_message === "注册成功！") {
                        error_message.value = resp.error_message;
                        setTimeout(() => {
                            // 使用Vue Router进行页面跳转
                            router.push({ name: "online_user_login" });
                        }, 1000);
                    } else {
                        error_message.value = resp.error_message;
                    }
                },
                error (resp) {
                    console.log(resp);
                }
            });
        }

        return {
            username,
            password,
            confirmed_password,
            phone,
            error_message,
            register,
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
</style>
