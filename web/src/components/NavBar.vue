<template>
    <!-- 默认显示的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="!$store.state.online_user.is_login && !$store.state.staff_user.is_login">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
        </div>
    </nav>
    <!-- 线上用户的narvar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark" v-else-if="$store.state.online_user.is_login">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link :class="route_name == 'reserve_room' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'reserve_room' }" role="button">
                            线上预约
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            线上用户 {{ $store.state.online_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'reservation_record' }">我的预约</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'online_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="online_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 酒店前台工作人员的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-else-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '酒店前台工作人员'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == front_desk_staff_working_page_list.working_page_1 || route_name == 'direct_check_in' || route_name == 'reserve_check_in' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: front_desk_staff_working_page_list.working_page_1 }" role="button">
                            办理入住
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == front_desk_staff_working_page_list.working_page_2 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: front_desk_staff_working_page_list.working_page_2 }" role="button">
                            办理退房
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == front_desk_staff_working_page_list.working_page_3 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: front_desk_staff_working_page_list.working_page_3 }" role="button">
                            修改房间信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 餐厅前台工作人员的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '餐厅前台工作人员'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == restaurant_staff_working_page_list.working_page_1 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: restaurant_staff_working_page_list.working_page_1 }" role="button">
                            点餐
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == restaurant_staff_working_page_list.working_page_2 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: restaurant_staff_working_page_list.working_page_2 }" role="button">
                            修改菜品信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 财务管理员的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '财务管理员'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == financial_staff_working_page_list.working_page_1 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: financial_staff_working_page_list.working_page_1 }" role="button">
                            登记工资财务信息
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == financial_staff_working_page_list.working_page_2 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: financial_staff_working_page_list.working_page_2 }" role="button">
                            查询常规财务信息
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == financial_staff_working_page_list.working_page_3 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: financial_staff_working_page_list.working_page_3 }" role="button">
                            查询采购财务信息
                        </router-link>
                    </li>
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == financial_staff_working_page_list.working_page_4 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: financial_staff_working_page_list.working_page_4 }" role="button">
                            查询工资财务信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 人事管理员的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '人事管理员'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == hr_staff_working_page_list.working_page_1 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: hr_staff_working_page_list.working_page_1 }" role="button">
                            修改/查询工作人员信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 采购人员的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '采购人员'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == purchase_staff_working_page_list.working_page_1 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: purchase_staff_working_page_list.working_page_1 }" role="button">
                            登记采购财务信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- 超级用户的navbar -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary navbar-dark bg-dark"
        v-if="$store.state.staff_user.is_login && $store.state.staff_user.duty === '超级用户'">
        <div class="container">
            <router-link class="navbar-brand" :to="{ name: 'home' }">酒店综合管理系统</router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item my-item">
                        <router-link
                            :class="route_name == superuser_working_page_list.working_page_1 ? 'nav-link active' : 'nav-link'"
                            :to="{ name: superuser_working_page_list.working_page_1 }" role="button">
                            修改工作人员账户信息
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            {{ $store.state.staff_user.duty }} {{ $store.state.staff_user.username }} 您好！
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'staff_user_info' }">用户信息</router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider" />
                            </li>
                            <li><a class="dropdown-item" href="#" @click="staff_user_logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { reactive } from 'vue';

export default {
    setup () {
        const store = useStore();
        const route = useRoute();
        let route_name = computed(() => route.name);

        //利用reactive定义酒店前台工作人员的working_page_list
        const front_desk_staff_working_page_list = reactive({
            working_page_1: "check_in",
            working_page_2: "check_out",
            working_page_3: "update_room",
        });

        //利用reactive定义餐厅前台工作人员的working_page_list
        const restaurant_staff_working_page_list = reactive({
            working_page_1: "order_food",
            working_page_2: "update_dish",
        });

        //利用reactive定义财务管理员的working_page_list
        const financial_staff_working_page_list = reactive({
            working_page_1: "register_salary",
            working_page_2: "query_general_financial_info",
            working_page_3: "query_purchase_financial_info",
            working_page_4: "query_salary_financial_info",
        });
        //利用reactive定义人事管理员的working_page_list
        const hr_staff_working_page_list = reactive({
            working_page_1: "update_staff",
        });
        //利用reactive定义采购人员的working_page_list
        const purchase_staff_working_page_list = reactive({
            working_page_1: "register_stuff",
        });
        //利用reactive定义超级用户的working_page_list
        const superuser_working_page_list = reactive({
            working_page_1: "update_user",
        });

        //当刷新页面时重新加载用户信息
        const default_working_page = localStorage.getItem("default_working_page");
        const online_user_username = localStorage.getItem("onlineuser_username");
        const staff_user_username = localStorage.getItem("staff_username");
        const staff_duty = localStorage.getItem("staff_duty");

        //如果是线上用户登录
        if (online_user_username) {
            store.commit('online_user/updateIsLogin', true);
            store.commit('online_user/updateUsername', online_user_username);
        }

        //如果是工作人员登录
        if (staff_user_username) {
            store.commit('staff_user/updateIsLogin', true);
            store.commit('staff_user/updateUsername', staff_user_username);
            store.commit('staff_user/updateDefaultWorkingPage', default_working_page);
            store.commit('staff_user/updateDuty', staff_duty);
        }

        const online_user_logout = () => {
            store.dispatch("online_user/logout");  //调用actions中的函数
        }

        const staff_user_logout = () => {
            store.dispatch("staff_user/logout");  //调用actions中的函数
        }

        return {
            route_name,
            online_user_logout,
            staff_user_logout,
            front_desk_staff_working_page_list,
            restaurant_staff_working_page_list,
            superuser_working_page_list,
            hr_staff_working_page_list,
            financial_staff_working_page_list,
            purchase_staff_working_page_list,
        }
    },
}
</script>

<style scoped>
.my-item {
    margin-left: 20px;
    margin-right: 20px;
}
</style>