import { createStore } from 'vuex'
import OnlineUser from './online_user'
import StaffUser from './staff_user'

export default createStore({
    state: {
    },
    getters: {
    },
    mutations: {
    },
    actions: {
    },
    modules: {
        //分成了一些子模块！
        online_user: OnlineUser,
        staff_user: StaffUser,
    }
})
