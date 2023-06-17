import homeMain from "@/components/home/HomeMain.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import mainRecommended from "@/components/home/main/MainRecommended.vue";
import mainFollowed from "@/components/home/main/MainFollowed.vue";
import mainHotTopices from "@/components/home/main/MainHotTopices.vue";
import mainVideos from "@/components/home/main/MainVideos.vue";
import home from "@/components/common/Home.vue";
import NotFound from "@/components/common/NotFound.vue";
import loginPage from "@/components/common/LoginPage.vue";
import homeUser from "@/components/user/HomeUser.vue";
import homeMember from "@/components/home/member/HomeMember.vue";
import homeHot from "@/components/home/hot/HomeHot.vue";
import homeDiscover from "@/components/home/discover/HomeDiscover.vue";
import homeQuestion from "@/components/home/question/HomeQuestion.vue";
import mainQuestion from "@/components/home/main/MainQuestion.vue";
import test from "@/components/common/Test.vue";
import homeCollection from "@/components/user/collection/HomeCollection.vue";
import homeWrite from "@/components/user/article/HomeWrite.vue";

const routes = [
    {
        path: '/', component: home,
        redirect: 'home-main',
        children: [
            {
                // 主界面
                name: 'home-main',
                path: 'home-main',
                component: homeMain,
                redirect: '/home-main/main-recommended',
                // 主界面下的五个菜单
                children: [
                    // 推荐界面-展示推荐问题的回答(帖子)
                    {name: 'main-recommended', path: 'main-recommended', component: mainRecommended},
                    {name: 'main-followed', path: 'main-followed', component: mainFollowed},
                    {name: 'main-hot-topics', path: 'main-hot-topics', component: mainHotTopices},
                    {name: 'main-videos', path: 'main-videos', component: mainVideos},
                    // 问题界面-展示发布的问题
                    {name: 'main-question', path: 'main-question', component: mainQuestion}
                ]
            },
            // 用户界面-展示用户信息，查询用户动态
            {name: 'home-user', path: 'home-user/:userId', component: homeUser},
            {name: 'home-collection', path: 'home-collection/:userId/:collectionId', component: homeCollection},
            {name: 'home-write', path: 'home-write', component: homeWrite},
            // 问题界面-展示发布问题的详细信息
            {name: 'home-question', path: 'home-question/:questionId', component: homeQuestion},
            {name: 'home-member', path: 'home-member', component: homeMember},
            {name: 'home-hot', path: 'home-hot', component: homeHot},
            {name: 'home-discover', path: 'home-discover', component: homeDiscover}
        ]
    },
    // 登录界面
    {path: '/login', name: 'login-page', component: loginPage},
    {path: '/test', name: 'test', component: test},
    // 404界面
    {path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound},


]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router
