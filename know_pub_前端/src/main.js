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
import Mock from "mockjs/dist/mock";

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

function mockSetUp() {
    let messages = []
    Mock.mock(/.*messages.*/, o => {
        let param = o.url.match(/.*pageSize=(\d+).*pageIndex=(\d+).*/)
        let pageSize = param[1]
        let pageIndex = param[2]

        for (let i = pageIndex; i < pageIndex + pageSize; i++) {
            let msg = Mock.mock({
                "content|1": [
                    '消息1',
                    '消息2',
                    '消息3',
                    '消息4',
                    '消息5',
                ]['content'],
                id: Mock.mock('@guid')
            })
            messages.push(msg)
        }
        return messages
    })
}

app.config.globalProperties.$mockSetUp = mockSetUp

app.mount('#app')
