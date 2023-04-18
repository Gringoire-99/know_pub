<template>
    <el-container>
        <el-header>
            <home-navbar/>
        </el-header>
        <el-main class="main">
            <el-row :gutter="10" class="main_content">
                <el-col :span="3">
                    <el-button>1</el-button>
                </el-col>
                <el-col :span="18">
                    <router-view></router-view>
                </el-col>

                <el-col :span="1">
                    <el-button>4</el-button>
                </el-col>
            </el-row>

        </el-main>

    </el-container>

</template>

<script>
import HomeNavbar from "@/components/home/HomeNavbar.vue";
export default {
    //组件名
    name: "app",
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

.main {
    margin-top: 10px;
}

</style>

