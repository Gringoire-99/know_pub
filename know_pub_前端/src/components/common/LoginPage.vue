<template>
    <div class="root w-100 ">
        <div class="h-25"></div>
        <div class="login">
            <login></login>
        </div>
        <div class="footer  d-flex flex-column justify-content-center align-items-center text-white mt-4">
            <div class="p-2">
                <span>知道吧专栏</span>·
                <span>主页</span>·
                <span>发现</span>·
                <span>头条</span>·
                <span>移动程序</span>·
                <span>联系我们</span>
            </div>
            <div class="p-2 ">
                <span>举报侵权</span>·
                <span>网上有害信息举报</span>·
                <span>违法不良信息举报</span>·
                <span>举报邮箱：g15985760072@outlook.com</span>
            </div>
            <div class="p-2 ">
                <span>version0.0.1</span>·
                <span>github:https://github.com/Gringoire-99/know_pub/tree/main</span>
            </div>

        </div>


    </div>

</template>

<script>
import {ArrowDown, Picture} from "@element-plus/icons-vue";
import Login from "@/components/common/Login.vue";

export default {
    //组件名
    name: "login-page",
    //依赖的组件
    components: {Login, ArrowDown, Picture},
    //数据
    data() {
        return {}
    }
    ,
//方法
    methods: {}
    ,
//挂载时执行
    mounted() {
    }
    ,
//创建时执行
    created() {
    }
    ,
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
.login {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1;
}

.root {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 5fr 1fr;
    height: 125vh;
    background: url("../../assets/login/login_bg.png") no-repeat scroll center;
    background-size: cover;
}


</style>
