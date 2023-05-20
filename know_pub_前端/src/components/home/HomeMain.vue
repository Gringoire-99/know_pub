<template>
    <div class="w-100 grid">
        <div class="space"></div>
        <div class="body-grid">
            <div class="card w-100 main">
                <div class="card-body">
                    <div class="card-title d-flex align-items-center">


                        <router-link v-for="(tab,index) in tabs" :key="index" :to="{path:`${tab.path}`}"
                        >
                            <button :class="{'selected':(index===currentTabIndex)}" class="btn"
                                    @click="onTabChange(index)">{{ tab.name }}
                            </button>
                        </router-link>

                    </div>
                    <div class="w-100">
                        <transition name="home-main">
                            <router-view>
                            </router-view>
                        </transition>


                    </div>
                </div>
            </div>
            <div class="sed">
                <main-secondary></main-secondary>
            </div>
        </div>
        <div class="space"></div>
    </div>
</template>

<script>
import MainList from "@/components/home/main/MainRecommended.vue";
import MainSecondary from "@/components/home/MainSecondary.vue";

export default {
    name: "home-main",
    components: {MainSecondary, MainList}, data() {
        return {
            currentTabIndex: 0,
            tabs: {
                'main-recommended': {name: '推荐', path: '/main-recommended'},
                'main-followed': {name: '关注', path: '/main-followed',},
                'main-hot-topics': {name: '热榜', path: '/main-hot-topics'},
                'main-videos': {name: '视频', path: '/main-videos'}
            }
        }
    },
    methods: {
        onTabChange(index) {
            this.currentTabIndex = index
        }
    },
    created() {
    }
}
</script>
<style lang="scss" scoped>
@include fade(home-main, 0.5s, -20px);
</style>
<style scoped>
.card-body .btn:focus {
    color: #0095da;
}


.card {
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border-radius: 1px;
    border: 1px solid #ebebeb;
}

.card-body .btn {
    border: none;
    font-size: 19px;
    white-space: nowrap;

}

.selected {
    color: #008cff !important;
}

.card-title {
    border-bottom: 1px solid #ebebeb;
    padding-bottom: 10px;
}

@media screen and (max-width: 600px) {
    .card-title {
        justify-content: center;
    }

}

@media screen and (max-width: 1300px) {
    .grid {
        grid-template-columns: 0fr 5fr 0fr !important;
    }


}

@media screen and (max-width: 1000px) {
    .body-grid {
        grid-template-columns: 1fr 0fr !important;
    }
}

.sed {
    min-width: 0;
    overflow: hidden;
}

.grid {
    display: grid;
    grid-template-columns: 1fr 5fr 1fr;
    grid-template-rows: 1fr;
    transition: all 0.5s;
}

.body-grid {
    display: grid;
    grid-template-columns: 9fr 4fr;
    grid-template-rows: 1fr;
    grid-gap: 10px;
    transition: all 0.5s;
}

</style>
