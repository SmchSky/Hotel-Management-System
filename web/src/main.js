import { createApp } from 'vue'
import App from './App.vue'
import VueRouter from './router'
import store from './store'

const app = createApp(App)
app.use(store).use(VueRouter)
app.mount('#app')

