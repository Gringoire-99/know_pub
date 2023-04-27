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
    Mock.mock(/.*recommended-posts.*/, o => {
        let posts = []
        let pageSize = 5
        for (let i = 0; i < pageSize; i++) {
            let post = {
                id: Mock.mock('@guid'),
                topic: Mock.mock('@ctitle(20,40)'),
                content: {
                    text: Mock.mock('@cparagraph(6,20)'),
                    images: Mock.mock({
                        "images|0-3": [
                            Mock.mock('@image("200x100")')
                        ]
                    })['images']
                },
                likes: Mock.mock('@integer(0,100)'),
                dislikes: Mock.mock('@integer(0,100)'),
                author: {
                    id: Mock.mock('@guid'),
                    name: Mock.mock('@cname'),
                    avatar: Mock.mock('@image("100x100")'),
                    desc: Mock.mock('@cparagraph(1,3)'),
                },
                publish_time: Mock.mock('@datetime'),
                update_time: Mock.mock('@datetime'),
            }
            posts.push(post)
        }
        console.log(posts)
        return posts
    })
    Mock.mock(/.*messages.*/, o => {
        let messages = []
        let param = o.url.match(/.*pageSize=(\d+).*pageIndex=(\d+).*/)
        let pageSize = parseInt(param[1])
        let pageIndex = parseInt(param[2])
        console.log(pageSize, pageIndex)
        for (let i = pageIndex; i < pageIndex + pageSize; i++) {
            let msg = {
                content: `${Mock.mock('@cname')}给你发了一条消息`,
                id: Mock.mock('@guid')
            }
            messages.push(msg)
        }
        return messages
    })
}
app.config.globalProperties.$mockSetUp = mockSetUp

app.mount('#app')
