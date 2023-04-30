import {createApp} from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import App from './App.vue'
import 'hover.css'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import ElementPlus, {ElNotification} from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import Router from "@/router";
import {store} from "../store";
import http from "@/utils/http/http";
import VueCookie from 'vue3-cookies'
import mockSetUp from "@/utils/mock/mock-setup";

const app = createApp(App)


function popUp(message, title, type) {
    ElNotification({
        title,
        message,
        type: type,
    })
}

app.config.globalProperties.$popUp = popUp
app.config.globalProperties.$http = http
app.config.globalProperties.$mockSetUp = mockSetUp
app.use(ElementPlus, {locale: zhCn,})
app.use(Router)
app.use(store)
app.use(VueCookie)
// 注册element-plus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


app.mount('#app')
export default app
