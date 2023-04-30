<template>
    <div>
        <el-row :gutter="10">
            <el-col :lg="18" :md="18" :sm="24" :xl="18" :xs="24">
                <div class="card w-100">
                    <div class="card-body">
                        <div class="card-title d-flex align-items-center">
                            <el-space :size="30">
                                <router-link :to="{path:'/main-followed'}">
                                    <button class="btn">关注</button>
                                </router-link>
                                <router-link :to="{path:'/main-recommended'}">
                                    <button :autofocus="true" class="btn">推荐</button>
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
            <el-col :span="6" class="d-none d-lg-inline ">
                <main-secondary></main-secondary>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import MainList from "@/components/home/main/MainRecommended.vue";
import MainSecondary from "@/components/home/MainSecondary.vue";

/**
 * 主界面的主要内容区域，用于展示帖子，和次要信息
 */
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
        this.$router.push({path: '/main-recommended'})
    }
}
</script>
<style>
.card-body .btn:focus {
    color: #0095da;
}

.card-body .btn {
    border: none;
    font-size: 19px;

}

.main {
    /*  底边*/
    border-top: 1px solid #ebebeb;
}
</style>

