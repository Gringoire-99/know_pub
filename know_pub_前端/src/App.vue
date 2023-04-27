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
                <el-col :lg="18" :md="18" :sm="24" :xl="18" :xs="24">
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
        this.$mockSetUp()
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

/* 滚动条整体 */
body::-webkit-scrollbar {
    width: 5px;
}

/* 两个滚动条交接处 -- x轴和y轴 */
body::-webkit-scrollbar-corner {
    background-color: transparent;
}

/* 滚动条滑块 */
body::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 5px rgb(7, 123, 255);
    background: #0794ff;

}

/* 滚动条轨道 */
body::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 5px rgba(215, 160, 160, 0.2);
    border-radius: 10px;
    background: #ededed;
}

/* 滚动条两端按钮 */
body::-webkit-scrollbar-button {
}
</style>

