import {createApp, unref} from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import App from './App.vue'
import 'hover.css'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import ElementPlus, {ElNotification} from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import Router from "@/router";

const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})
app.use(Router)

function popUp(message, title, type) {
    ElNotification({
        title,
        message,
        type: type,
    })
}

app.config.globalProperties.$popUp = popUp
// 注册element-plus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')

