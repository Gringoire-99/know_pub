<template>
    <div class="login-root">
        <div class="space"></div>
        <div class="main">
            <div class="space"></div>
            <login></login>
            <div class="footer text-white ">
                <div>
                    <span>知道吧专栏</span>·
                    <span>主页</span>·
                    <span>发现</span>·
                    <span>头条</span>
                </div>
                <div>
                    <span>移动程序</span>·
                    <span>联系我们</span>·
                    <span>version0.0.1</span>·
                    <span>github:https://github.com/Gringoire-99/know_pub/tree/main</span>
                </div>
                <div>
                    <span>举报侵权</span>·
                    <span>网上有害信息举报</span>·
                    <span>违法不良信息举报</span>·
                    <span>举报邮箱：g15985760072@outlook.com</span>
                </div>


            </div>
            <div class="space"></div>
        </div>
        <div class="space"></div>
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
        if (this.$store.state.reload) {
            location.reload()
        }
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

<style lang="scss" scoped>

.login-root {
    @include align(grid);
    grid-template-columns:1fr 2fr 1fr;
    height: 100vh;
    background: url("../../assets/login/login_bg.png") no-repeat scroll center;
    background-size: cover;
    overflow-x: hidden;
    overflow-y: scroll;
    transition: grid 0.5s;
    @media screen and (max-width: 1200px) {
        grid-template-columns:1fr 4fr 1fr;
    }
    @media screen and (max-width: 800px) {
        grid-template-columns:1fr 8fr 1fr;
    }

    .main {
        @include align(grid);
        grid-template-rows: 1fr 5fr 1fr;
        flex-shrink: 0;

        .footer {
            div {
                @include align();
                flex-wrap: wrap;

                span {
                    @include hoverFloat();
                    @include clickable();
                    color: $white;
                    text-align: center;
                }
            }
        }
    }
}


</style>
