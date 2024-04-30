import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import ReserveRoom from '@/views/customer/reserve/ReserveRoom.vue'
import OnlineUserLogin from '@/views/customer/login_register/OnlineUserLogin.vue'
import TestRegister from '@/views/customer/login_register/OnlineUserRegister.vue'
import StaffUserLogin from '@/views/administrator/login/StaffUserLogin'
import RegisterSalary from '@/views/administrator/financial_staff/RegisterSalary'
import QueryGeneralFinancialInfo from '@/views/administrator/financial_staff/QueryGeneralFinancialInfo'
import QueryPurchaseFinancialInfo from '@/views/administrator/financial_staff/QueryPurchaseFinancialInfo'
import QuerySalaryFinancialInfo from '@/views/administrator/financial_staff/QuerySalaryFinancialInfo'
import UpdateStaff from '@/views/administrator/hr_staff/UpdateStaff'
import RegisterStuff from '@/views/administrator/purchase_staff/RegisterStuff'
import UpdateUser from '@/views/administrator/superuser/UpdateUser'
import OnlineUserInfo from '@/views/customer/user_info/OnlineUserInfo'
import StaffUserInfo from '@/views/administrator/user_info/StaffUserInfo'
import CheckIn from '@/views/administrator/front_desk_staff/CheckIn'
import CheckOut from '@/views/administrator/front_desk_staff/CheckOut'
import UpdateRoom from '@/views/administrator/front_desk_staff/UpdateRoom'
import OrderFood from '@/views/administrator/restaurant_staff/OrderFood'
import UpdateDish from '@/views/administrator/restaurant_staff/UpdateDish'
import DirectCheckIn from '@/views/administrator/front_desk_staff/DirectCheckIn'
import ReserveCheckIn from '@/views/administrator/front_desk_staff/ReserveCheckIn'
import ReservationRecord from '@/views/customer/reserve/ReservationRecord'
import store from '../store/index'


const routes = [

    {
        redirect: "/hr_staff/update_staff",
        name: 'hr_staff_default_working_page',
        component: UpdateStaff,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/hr_staff/update_staff',
        name: 'update_staff',
        component: UpdateStaff,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        redirect: "/financial_staff/query_general_financial_info",
        name: 'financial_staff_default_working_page',
        component: QueryGeneralFinancialInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/financial_staff/query_general_financial_info',
        name: 'query_general_financial_info',
        component: QueryGeneralFinancialInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/financial_staff/query_salary_financial_info',
        name: 'query_salary_financial_info',
        component: QuerySalaryFinancialInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/financial_staff/query_purchase_financial_info',
        name: 'query_purchase_financial_info',
        component: QueryPurchaseFinancialInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/financial_staff/register_salary',
        name: 'register_salary',
        component: RegisterSalary,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        redirect: "/restaurant_staff/order_food",
        name: 'restaurant_staff_default_working_page',
        component: OrderFood,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/restaurant_staff/update_dish',
        name: 'update_dish',
        component: UpdateDish,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/restaurant_staff/order_food',
        name: 'order_food',
        component: OrderFood,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        redirect: "/front_desk_staff/check_in",
        name: 'front_desk_staff_default_working_page',
        component: CheckIn,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/front_desk_staff/reserve_check_in',
        name: 'reserve_check_in',
        component: ReserveCheckIn,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/front_desk_staff/direct_check_in',
        name: 'direct_check_in',
        component: DirectCheckIn,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/front_desk_staff/check_in',
        name: 'check_in',
        component: CheckIn,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/front_desk_staff/check_out',
        name: 'check_out',
        component: CheckOut,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/front_desk_staff/update_room',
        name: 'update_room',
        component: UpdateRoom,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/online_user_info',
        name: 'online_user_info',
        component: OnlineUserInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/staff_user_info',
        name: 'staff_user_info',
        component: StaffUserInfo,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        redirect: "/superuser/update_user",
        name: 'superuser_default_working_page',
        component: UpdateUser,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/superuser/update_user',
        name: 'update_user',
        component: UpdateUser,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        redirect: "/purchase_staff/register_stuff",
        name: 'purchase_staff_default_working_page',
        component: RegisterStuff,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/purchase_staff/register_stuff',
        name: 'register_stuff',
        component: RegisterStuff,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/hotel/',
        name: 'home',
        component: HomePage,
        meta: {
            requestAuth: false,   //是否需要登录授权
        }
    },
    {
        path: '/online_user/reservation_record',
        name: 'reservation_record',
        component: ReservationRecord,
        meta: {
            requestAuth: true,   //是否需要登录授权
        }
    },
    {
        path: '/online_user/reserve_room',
        name: 'reserve_room',
        component: ReserveRoom,
        meta: {
            requestAuth: true,   //是否需要登录授权
        },
    },
    {
        path: '/online_user_login',
        name: "online_user_login",
        component: OnlineUserLogin,
        meta: {
            requestAuth: false,   //是否需要登录授权
        },
    },
    {
        path: '/staff_user_login',
        name: "staff_user_login",
        component: StaffUserLogin,
        meta: {
            requestAuth: false,   //是否需要登录授权
        },
    },
    {
        path: '/register',
        name: "register",
        component: TestRegister,
        meta: {
            requestAuth: false,   //是否需要登录授权
        }
    },
]


const VueRouter = createRouter({
    history: createWebHistory(),
    routes,  //键和值一样时可以简写
})

VueRouter.beforeEach((to, from, next) => {
    if (to.meta.requestAuth && !store.state.online_user.is_login && !store.state.staff_user.is_login) {  //该页面需要登录授权并且用户还未登录
        next({ name: "home" })
    } else {
        next();  //跳转到默认页面
    }
})
export default VueRouter
