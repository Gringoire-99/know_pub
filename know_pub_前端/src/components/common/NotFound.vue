<template>
    <div class="card d-flex flex-column align-items-center justify-content-center">
        <div class="card-body d-flex">
            <img alt="" class="card-img " src="../../assets/not_find/not-find.png"/>
        </div>
        <div class="card-footer w-100 d-flex flex-column align-items-center justify-content-center">
            <div class="fs-1 fw-bold">404</div>
            <div class="fs-4 fw-bold">页面不存在</div>
            <div class="fs-6 fw-light">请检查您输入的网址是否正确，请点击以下按钮返回主页或者发送错误报告</div>
            <div class="d-flex flex-row justify-content-center align-items-center">
                <router-link :to="{path:'/home-main'}">
                    <el-button class="m-2" type="primary">返回主页</el-button>
                </router-link>
                <el-button class="m-2" type="primary">发送错误报告</el-button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    //组件名
    name: "not-found",
    //依赖的组件
    components: {},
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

</style>
