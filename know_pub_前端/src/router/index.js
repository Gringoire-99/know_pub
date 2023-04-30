import homeMain from "@/components/home/HomeMain.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import mainRecommended from "@/components/home/main/MainRecommended.vue";
import mainFollowed from "@/components/home/main/MainFollowed.vue";
import mainHotTopices from "@/components/home/main/MainHotTopices.vue";
import mainVideos from "@/components/home/main/MainVideos.vue";
import login from "@/components/common/login.vue";
import root from "@/components/common/root.vue";
import home from "@/components/common/home.vue";
import NotFound from "@/components/common/NotFound.vue";

const routes = [
    {
        path: '/', component: root
        , children: [
            {name: 'login', path: '/login', component: login},
            {
                name: 'home', path: '/home', component: home, children: [
                    {
                        name: 'home-main', path: '/home-main', component: homeMain, children: [
                            {name: 'main-recommended', path: '/main-recommended', component: mainRecommended},
                            {name: 'main-followed', path: '/main-followed', component: mainFollowed},
                            {name: 'main-hot-topics', path: '/main-hot-topics', component: mainHotTopices},
                            {name: 'main-videos', path: '/main-videos', component: mainVideos},
                        ]
                    }
                ]
            },
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
