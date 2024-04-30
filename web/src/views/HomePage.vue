<template>
    <div class="welcome_board" v-if="!$store.state.online_user.is_login && !$store.state.staff_user.is_login">
        <div class="row-6">
            <div class="welcome_word">
                欢迎使用酒店综合管理系统!
            </div>
            <div class="welcome_word">
                请选择您的身份：
            </div>
        </div>
        <div class="row-6 button-container">
            <router-link to="/online_user_login">
                <button type="button" class="btn btn-lg btn-warning my_button">我是顾客</button>
            </router-link>
            <router-link to="/staff_user_login">
                <button type="button" class="btn btn-lg btn-info my_button">我是工作人员</button>
            </router-link>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import router from '@/router/index'

export default {
    setup () {
        const store = useStore();
        if (store.state.online_user.is_login) {
            console.log("用户已登录");
            router.push({ name: "reserve_room" });
        }

        if (store.state.staff_user.is_login) {
            console.log("员工已登录");
            router.push({ name: store.state.staff_user.default_working_page });
        }

        return {}
    }
}
</script>

<style>
div.welcome_board {
    width: 80vw;
    /* 60vw : 60%浏览器宽度 */
    height: 80vh;
    /* 70vw : 70%浏览器高度 */
    margin: 50px auto;

    background-color: rgba(74, 101, 102, 0.636);
}

div.welcome_word {
    text-align: center;
    font-size: 60px;
    font-weight: 900;
    color: white;
    padding-top: 8vh;
    font-family: monospace;
}

.button-container {
    margin-top: 12vh;
    display: grid;
    grid-template-columns: 1fr 1fr;
    align-items: stretch;
    justify-items: center;
    align-content: center;
}

.my_button {
    width: 200px;
    height: 80px;
    font-size: 150%;
}
</style>
