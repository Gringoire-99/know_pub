<template>
    <div class="w-100 h-100">
        <home-navbar/>
        <div class="main d-flex">


            <router-view>
            </router-view>
        </div>


    </div>

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
<style lang="scss" scoped>


.main {
    padding-top: 70px;
    overflow-x: hidden;
}


</style>
<style>


</style>

