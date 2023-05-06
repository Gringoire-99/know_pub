<template>
    <div>
        <el-row :gutter="10">
            <el-col :lg="17" :md="17" :sm="24" :xl="24" :xs="24">
                <div class="card w-100">
                    <div class="card-body">
                        <div class="card-title d-flex align-items-center">
                            <el-space :size="30">
                                <router-link :to="{path:'/main-followed'}">
                                    <button class="btn">关注</button>
                                </router-link>
                                <router-link :to="{path:'/main-recommended'}">
                                    <button class="btn">推荐</button>
                                </router-link>
                                <router-link :to="{path:'/main-hot-topics'}">
                                    <button class="btn">热榜</button>
                                </router-link>
                                <router-link :to="{path:'/main-videos'}">
                                    <button class="btn">视频</button>
                                </router-link>
                            </el-space>
                        </div>
                        <div class="w-100">
                            <router-view></router-view>
                        </div>
                    </div>
                </div>
            </el-col>
            <el-col :span="7" class="d-none d-lg-inline ">
                <main-secondary></main-secondary>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import MainList from "@/components/home/main/MainRecommended.vue";
import MainSecondary from "@/components/home/MainSecondary.vue";

export default {
    name: "home-main",
    components: {MainSecondary, MainList},
    methods: {
        getView() {
            console.log("获取视图")
        }
    },
    created() {
        //     router-view 默认为推荐页面
    }
}
</script>
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

}


.card-title {
    border-bottom: 1px solid #ebebeb;
    padding-bottom: 10px;
}
</style>
