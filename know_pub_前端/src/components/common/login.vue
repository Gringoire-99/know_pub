<template>
    <div class="root d-flex flex-column justify-content-center align-items-center">
        <div class="h-25"></div>
        <div class="login d-flex row-cols-2 mb-4">
            <div class="qr col-5 pt-5 d-flex flex-column justify-content-center align-items-center">
                <div class="p-1 fw-bold fs-6 mt-3">打开知道吧APP</div>
                <div class="p-1 fw-light fs-6">在[首页]左上角打开扫一扫</div>
                <img class="qrcode mt-4 mb-2" src="../../assets/login/qr_login.png"/>
                <div class="p-2 fw-bold fs-6 mt-4 mb-4">其他扫码方式：微信</div>
                <div class="d-inline">
                    <el-button>下载知道吧app</el-button>
                    <el-button>开通机构号</el-button>
                    <el-button>无障碍模式</el-button>
                </div>
            </div>
            <div class="login-form col-7">
                <el-tabs v-model="activeName" class="">
                    <el-tab-pane class="mb-4" label="验证码登录" name="first">
                        <div class="form-phone d-flex row-cols-2 mt-5">
                            <el-popover
                                placement="bottom"
                                popper-class="phone-prefix-popover"
                                trigger="click"
                                width="200"
                            >
                                <template #reference>
                                    <el-button class="phone-prefix d-flex col-3">
                                        <span>{{ loginForm.phone.label }}</span><span>+{{
                                            loginForm.phone.prefix
                                        }}</span>
                                        <el-icon class="ms-1">
                                            <arrow-down></arrow-down>
                                        </el-icon>
                                    </el-button>
                                </template>
                                <ul class="prefix-list d-flex flex-column">
                                    <li v-for="item in phonePrefix" :key="item.value" class=" w-100"
                                        @click="loginForm.phone.prefix = item.value;loginForm.phone.label = item.label">
                                        <div class="phone-prefix d-flex ">
                                            <span>{{ item.label }}</span><span>+{{ item.value }}</span>
                                        </div>
                                    </li>
                                </ul>
                            </el-popover>
                            <div class="el-divider--vertical h-75 align-self-center"></div>
                            <input v-model="loginForm.phone.number" class="phone col-9" placeholder="手机号">
                        </div>
                        <div class="form-captcha d-flex row-cols-2 mt-3 ">
                            <input v-model="loginForm.captcha" class="captcha-input col-9"
                                   placeholder="请输入6位验证码">
                            <button class="col-3 captcha-btn">获取短信验证码</button>
                        </div>
                        <div class="form-etc d-flex p-2 mt-3 mb-3 justify-content-end ">
                            <button class="voice-captcha-btn ">获取语音验证码</button>
                        </div>
                        <div class="submit-btn d-flex">
                            <button class="w-100">登录/注册</button>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane class="mb-4" label="密码登录" name="second">
                        <div class="form-phone d-flex mt-5">
                            <input v-model="loginFormByPassword.account" class="phone w-100" placeholder="手机号或邮箱">
                        </div>
                        <div class="form-captcha d-flex  mt-3 ">
                            <input v-model="loginFormByPassword.password" class="phone w-100" placeholder="密码"
                                   type="password">

                        </div>
                        <div class="form-etc d-flex p-2 mt-3 mb-3 justify-content-end ">
                            <button class="voice-captcha-btn ">忘记密码</button>
                        </div>
                        <div class="submit-btn d-flex">
                            <button class="w-100">登录</button>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <div class="other-way mb-3">
                    <div class="d-flex justify-content-center align-items-center mb-3">
                        <div class="el-divider--horizontal"></div>
                        <span class="w-100 text-center">其他方式登录</span>
                        <div class="el-divider--horizontal"></div>
                    </div>
                    <div class="other-way-icons d-flex justify-content-center align-content-center mb-3">
                        <img src="../../assets/login/wechat.svg">
                        <img src="../../assets/login/QQ.svg">
                        <img src="../../assets/login/github.svg">
                    </div>
                    <div class="mt-5 mb-5">
                        <span>未注册手机验证后自动登录，注册即代表同意
                            <span class="agreement text-primary">《知道吧协议》</span>
                            <span class="agreement text-primary">《隐私保护指引》</span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
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
import {ArrowDown, Picture} from "@element-plus/icons-vue";

export default {
    //组件名
    name: "login",
    //依赖的组件
    components: {ArrowDown, Picture},
    //数据
    data() {
        return {
            loginForm: {
                phone: {
                    prefix: '86',
                    number: '',
                    label: '中国大陆'
                },
                password: '',
                captcha: ''

            },
            loginFormByPassword: {
                account: "",
                password: '',
            }
            ,
            activeName: 'first',
            phonePrefix: [
                {
                    value: '86',
                    label: '中国大陆'
                },
                {
                    value: '852',
                    label: '中国香港'
                },
                {
                    value: '853',
                    label: '中国澳门'
                },
                {
                    value: '886',
                    label: '中国台湾'
                },
                {
                    value: '1',
                    label: '美国'
                },
                {
                    value: '44',
                    label: '英国'
                },
                {
                    value: '33',
                    label: '法国'
                },
                {
                    value: '7',
                    label: '俄罗斯'
                },
                {
                    value: '39',
                    label: '意大利'
                },
                {
                    value: '81',
                    label: '日本'
                },
                {
                    value: '82',
                    label: '韩国'
                },
                {
                    value: '65',
                    label: '新加坡'
                },
                {
                    value: '60',
                    label: '马来西亚'
                },
                {
                    value: '63',
                    label: '菲律宾'
                },
                {
                    value: '66',
                    label: '泰国'
                },
                {
                    value: '84',
                    label: '越南'
                },
                {
                    value: '62',
                    label: '印度尼西亚'
                },
                {
                    value: '55',
                    label: '巴西'
                },
                {
                    value: '351',
                    label: '葡萄牙'
                },
                {
                    value: '49',
                    label: '德国'
                },
                {
                    value: '46',
                    label: '瑞典'
                },
                {
                    value: '41',
                    label: '瑞士'
                },
                {
                    value: '31',
                    label: '荷兰'
                },
                {
                    value: '32',
                    label: '比利时'
                },
                {
                    value: '47',
                    label: '挪威'
                },
                {
                    value: '45',
                    label: '丹麦'
                },
                {
                    value: '48',
                    label: '波兰'
                },
                {
                    value: '34',
                    label: '西班牙'
                },
                {
                    value: '30',
                    label: '希腊'
                },
                {
                    value: '36',
                    label: '匈牙利'
                }

            ],
        }
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

.other-way span {
    font-weight: lighter;
    color: #696868;
}

.agreement {
    cursor: pointer;
}

.other-way-icons img {
    width: 50px;
    height: 50px;
    margin: 0 50px;
    cursor: pointer;
}

.submit-btn button {
    height: 50px;
    background: #056de8;
    color: white;
    border: 1px solid #056de8;
    border-radius: 5px;
}

.submit-btn button:hover {
    background: #2b80e3;
}

:deep(.el-tabs__nav-wrap::after) {
    width: 0;
}

.form-phone, .form-captcha {
    padding-bottom: 5px;
    border-bottom: 1px solid rgba(220, 223, 230, 0.51);
    height: 70px;
}

.prefix-list {
    height: 200px;
    overflow-y: scroll;
    overflow-x: hidden;
    padding: 0;
}

.prefix-list li {
    /*    取消默认样式*/
    list-style: none;
    padding: 0 0;
    cursor: pointer;
    height: 25px;
    margin-bottom: 10px;
}

.prefix-list li:hover {
    color: #fff;
    background: #409eff;
}

.phone-prefix {
    border: none;
    outline: none;
    font-size: 18px;
    height: 100%;
}

.phone {
    margin-left: 15px;
    border: none;
    outline: none;
}

.captcha-input {
    border: none;
    outline: none;
}

.captcha-btn, .voice-captcha-btn {
    color: #056de8;
    border: none;
    background: none;
}

.captcha-btn:hover, .voice-captcha-btn:hover {
    color: #409eff;
}

.login-form {
    padding: 20px 20px;
}

.qr {
    border-right: 1px solid rgba(204, 204, 204, 0.38);
}


.login {
    background: #fff;
    width: 1000px;
    padding-top: 20px;
    padding-bottom: 20px;
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

    height: 125vh;
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
    width: 200px;
    height: 200px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

.voice-captcha-btn {
    align-self: center;
}

.footer span:hover {
    /*下划线*/
    text-decoration: underline;
    cursor: pointer;
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

/*改变tab文字大小*/
:deep(.el-tabs__item) {
    font-weight: bold;
    font-size: 20px;
}

/* 滚动条整体 */
.prefix-list::-webkit-scrollbar {
    width: 5px;
}

/* 两个滚动条交接处 -- x轴和y轴 */
.prefix-list::-webkit-scrollbar-corner {
    background-color: transparent;
}

/* 滚动条滑块 */
.prefix-list::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 5px rgb(7, 123, 255);
    background: #0794ff;

}

/* 滚动条轨道 */
.prefix-list::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 5px rgba(215, 160, 160, 0.2);
    border-radius: 10px;
    background: #ededed;
}

/* 滚动条两端按钮 */
.prefix-list::-webkit-scrollbar-button {
}
</style>
