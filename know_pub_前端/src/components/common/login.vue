<template>
    <div class="root d-flex flex-column justify-content-center align-items-center">
        <div class="h-25"></div>
        <el-row class="login d-flex mb-4 hvr-grow">
            <el-col :span="10" class="qr h-100 d-flex flex-column justify-content-center align-items-center">
                <div class="p-1 fw-bold fs-6">打开知道吧APP</div>
                <div class="p-1 fw-light fs-6">在[首页]左上角打开扫一扫</div>
                <img class="qrcode mt-4 mb-2" src="../../assets/login/qr_login.png"/>
                <div class="p-2 fw-bold fs-6 mt-4 mb-4">其他扫码方式：微信</div>
                <div class="d-inline">
                    <el-button>下载知道吧app</el-button>
                    <el-button>开通机构号</el-button>
                    <el-button>无障碍模式</el-button>
                </div>
            </el-col>
            <el-col :span="12" class="login-form"></el-col>
        </el-row>
        <div class="footer  d-flex flex-column justify-content-center align-items-center text-white">
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
import {Picture} from "@element-plus/icons-vue";

export default {
    //组件名
    name: "login",
    //依赖的组件
    components: {Picture},
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
.etc {
    color: #fff;
}

.qr {
    padding-top: 100px;
    border-right: 1px solid rgba(204, 204, 204, 0.57);
}


.login {
    width: 800px;
    background: #fff;
}

.login:hover {
    /*    背光*/
    box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.1);
}

img {
    width: 500px;
    height: 200px;
}

.root {

    height: 115vh;
    /*渐变*/
    background: url("../../assets/login/login_bg.png") no-repeat scroll center;
    background-size: 100% 100%;


}

.qr .el-button {
    font-weight: lighter;
    font-size: 10px;
    padding: 5px;
    border-radius: 20px;
    margin-top: 50px;
    margin-bottom: 50px
}

.qrcode {
    width: 150px;
    height: 150px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

.footer {
}

.footer span:hover {
    /*下划线*/
    text-decoration: underline;
    cursor: pointer;
}

.hvr-grow {
    display: inline-block;
    vertical-align: middle;
    -webkit-transform: perspective(1px) translateZ(0);
    transform: perspective(1px) translateZ(0);
    box-shadow: 0 0 1px rgba(0, 0, 0, 0);
    -webkit-transition-duration: 0.3s;
    transition-duration: 0.3s;
    -webkit-transition-property: transform;
    transition-property: transform;
}

.hvr-grow:hover, .hvr-grow:focus, .hvr-grow:active {
    -webkit-transform: scale(1.05);
    transform: scale(1.05);
    box-shadow: rgba(0, 0, 0, 0.15) 0px 15px 25px, rgba(0, 0, 0, 0.05) 0px 5px 10px;

}

/* Float */
.footer span {
    display: inline-block;
    vertical-align: middle;
    -webkit-transform: perspective(1px) translateZ(0);
    transform: perspective(1px) translateZ(0);
    box-shadow: 0 0 1px rgba(0, 0, 0, 0);
    -webkit-transition-duration: 0.3s;
    transition-duration: 0.3s;
    -webkit-transition-property: transform;
    transition-property: transform;
    -webkit-transition-timing-function: ease-out;
    transition-timing-function: ease-out;
}

.footer span:hover, .footer span:focus, .footer span:active {
    -webkit-transform: translateY(-3px);
    transform: translateY(-3px);
}
</style>
