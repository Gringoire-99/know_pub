import homeMain from "@/components/home/HomeMain.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import hotSpots from "@/components/hot-spots/hot-spots.vue";

const routes = [
    {path: '/', component: homeMain},
    {path: '/icon', component: homeMain},
    {path: '/homeMain', component: homeMain},
    {path: '/hotSpots', component: hotSpots},
]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router
