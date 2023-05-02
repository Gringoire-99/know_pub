<template>
    <el-container class="w-100 h-100">
        <el-header>
            <el-affix :offset="0.1">
                <home-navbar/>
            </el-affix>
        </el-header>
        <el-main class="main">
            <el-row :gutter="10" class="main_content">
                <el-col :span="3" class="d-none d-lg-inline ">
                </el-col>
                <el-col :lg="18" :md="18" :sm="24" :xl="24" :xs="24">
                    <router-view></router-view>
                </el-col>
                <el-col :span="1" class="d-none d-lg-inline ">
                </el-col>
            </el-row>

        </el-main>

    </el-container>

</template>
<script>
import HomeNavbar from "@/components/nav/NavBar.vue";

export default {
    //组件名
    name: "",
    //依赖的组件
    components: {HomeNavbar},
    //数据
    data() {
        return {}
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.$router.push({path: '/main-recommended'})
    },
    //侦听器
    watch: {
        // 每当 question 改变时，这个函数就会执行
        // question(newQuestion, oldQuestion) {}
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>
<style scoped>

.home-navbar {
    overflow: hidden;
    white-space: nowrap;
}

.el-header {
    padding-right: 0;
    padding-left: 0;
}

</style>
<style>
body {
    overflow-x: hidden;
}

</style>

