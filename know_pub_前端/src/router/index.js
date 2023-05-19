import homeMain from "@/components/home/HomeMain.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import mainRecommended from "@/components/home/main/MainRecommended.vue";
import mainFollowed from "@/components/home/main/MainFollowed.vue";
import mainHotTopices from "@/components/home/main/MainHotTopices.vue";
import mainVideos from "@/components/home/main/MainVideos.vue";
import root from "@/components/common/Root.vue";
import home from "@/components/common/Home.vue";
import NotFound from "@/components/common/NotFound.vue";
import loginPage from "@/components/common/LoginPage.vue";
import test from "@/components/common/Test.vue";
import homeUser from "@/components/user/HomeUser.vue";
import homeMember from "@/components/home/member/HomeMember.vue";
import homeHot from "@/components/home/hot/HomeHot.vue";
import homeDiscover from "@/components/home/discover/HomeDiscover.vue";
import homeQuestion from "@/components/home/question/HomeQuestion.vue";

const routes = [
    {
        path: '/', component: root,
        redirect: 'home'
        , children: [
            {name: 'login-page', path: '/login', component: loginPage},
            {
                name: 'home', path: '/home', component: home, redirect: '/home-main', children: [
                     {
                        name: 'home-main',
                        path: '/home-main',
                        redirect: '/main-recommended',
                        component: homeMain,
                        children: [
                            {name: 'main-recommended', path: '/main-recommended', component: mainRecommended},
                            {name: 'main-followed', path: '/main-followed', component: mainFollowed},
                            {name: 'main-hot-topics', path: '/main-hot-topics', component: mainHotTopices},
                            {name: 'main-videos', path: '/main-videos', component: mainVideos},
                        ]
                    },
                    {name: 'home-user', path: '/home-user/:userId', component: homeUser},
                    {name: 'home-member', path: '/home-member', component: homeMember},
                    {name: 'home-hot', path: '/home-hot', component: homeHot},
                    {name: 'home-discover', path: '/home-discover', component: homeDiscover},
                    {name:'home-question',path: '/home-question/:questionId',component: homeQuestion}
                ]
            },
            {path: 'test', name: 'test', component: test},
            {path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound},
        ]
    },


//     TODO 404页面


]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router
