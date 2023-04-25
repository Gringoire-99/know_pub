import homeMain from "@/components/home/HomeMain.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import hotSpots from "@/components/hot-spots/hot-spots.vue";
import mainRecommended from "@/components/home/main/MainRecommended.vue";
import mainFollowed from "@/components/home/main/MainFollowed.vue";
import mainHotTopices from "@/components/home/main/MainHotTopices.vue";
import mainVideos from "@/components/home/main/MainVideos.vue";

const routes = [
    {path: '/', component: homeMain},
    {
        path: '/home-main', component: homeMain,
        children: [
            {name: 'main-recommended', path: 'main-recommended', component: mainRecommended},
            {name: 'main-followed', path: 'main-followed', component: mainFollowed},
            {name: 'main-hot-topics', path: 'main-hot-topics', component: mainHotTopices},
            {name: 'main-videos', path: 'main-videos', component: mainVideos},
        ]
    },
    {path: '/hot-spots', component: hotSpots},

]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router
