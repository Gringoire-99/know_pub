<template>
    <div class="login">
        <div class="qr">
            <div class="qr-header">
                <div class="p-1 fw-bold fs-6"><span>打开知道吧</span></div>
                <div class="p-1 fw-light fs-6">在[首页]左上角打开扫一扫</div>
            </div>
            <div class="qr-body">
                <div><img alt="qr" class="qrcode justify-content-center" src="../../assets/login/qr_login.png"/></div>
                <div class="p-2 fw-bold fs-6 ">其他扫码方式：微信</div>
            </div>

            <div class="qr-footer">
                <el-button>下载知道吧</el-button>
                <el-button>开通机构号</el-button>
                <el-button>无障碍模式</el-button>
            </div>
        </div>
        <div class="login-form w">
            <div class="form-body">
                <el-tabs v-model="activeName" class="">
                    <el-tab-pane class="" label="验证码登录" name="phone">
                        <form class="form">
                            <div class="username">
                                <div>
                                    <el-popover
                                        placement="bottom"
                                        popper-class="phone-prefix-popover"
                                        trigger="click"
                                        width="200"
                                    >
                                        <template #reference>
                                            <el-button class="phone-prefix">
                                                <span class="prefix-label">{{ loginForm.phone.label }}</span><span>+{{
                                                    loginForm.phone.prefix
                                                }}</span>
                                                <el-icon>
                                                    <arrow-down></arrow-down>
                                                </el-icon>
                                            </el-button>
                                        </template>
                                        <ul class="prefix-list">
                                            <li v-for="item in phonePrefix" :key="item.value" class="list-item"
                                                @click="loginForm.phone.prefix = item.value;loginForm.phone.label = item.label">
                                                <div class="phone-prefix">
                                                    <span>{{ item.label }}</span><span>+{{ item.value }}</span>
                                                </div>
                                            </li>
                                        </ul>
                                    </el-popover>
                                </div>
                                <div class="el-divider--vertical"></div>
                                <input v-model="loginForm.phone.number" class="phone" placeholder="手机号">
                            </div>
                            <div class="captcha">
                                <input v-model="loginForm.captcha" class="captcha-input"
                                       placeholder="请输入6位验证码">
                                <button class="text-btn">获取短信验证码</button>
                            </div>
                            <div class="pane-footer">
                                <div class="get-captcha">
                                    <button class="text-btn">获取语音验证码</button>
                                </div>
                            </div>
                            <div class="submit-btn">
                                <button class="w-100" @click="loginBySms">登录/注册</button>
                            </div>
                        </form>

                    </el-tab-pane>
                    <el-tab-pane class="" label="密码登录" name="passwd">
                        <form class="form">

                            <div class="username">
                                <input v-model="loginFormByPassword.username" autocomplete="username"
                                       class="phone"
                                       placeholder="手机号或邮箱">
                            </div>
                            <div class="passwd">
                                <input v-model="loginFormByPassword.password" autocomplete="current-password"
                                       class="phone" placeholder="密码"
                                       type="password">
                            </div>


                            <div class="pane-footer">
                                <div v-show="!isLoading" class="get-passwd ms-auto">
                                    <button class="text-btn">忘记密码</button>
                                </div>
                                <el-progress v-show="isLoading" :indeterminate="true"
                                             :percentage="100"/>

                            </div>
                            <div class="submit-btn">
                                <button class="w-100" @click="login">登录</button>
                            </div>
                        </form>

                    </el-tab-pane>
                    <el-tab-pane label="注册" name="register">
                        <form>
                            <form class="form">

                                <div class="username">
                                    <input v-model="registerForm.username" autocomplete="username"
                                           class="phone"
                                           placeholder="手机号或邮箱">
                                </div>
                                <div class="passwd">
                                    <input v-model="registerForm.password" autocomplete="current-password"
                                           class="phone" placeholder="密码"
                                           type="password">
                                </div>
                                <div class="pane-footer">

                                </div>
                                <div class="submit-btn">
                                    <button class="w-100" @click="register">注册</button>
                                </div>
                            </form>
                        </form>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <div class="form-footer">
                <div class="">
                    <div class="el-divider--horizontal"></div>
                    <span class="w-100 text-center">其他方式登录</span>
                    <div class="el-divider--horizontal"></div>
                </div>
                <div class="other-way-icons ">
                    <el-icon>
                        <q-q></q-q>
                    </el-icon>
                    <el-icon>
                        <we-chat></we-chat>
                    </el-icon>
                    <el-icon>
                        <github></github>
                    </el-icon>
                </div>
                <div class="">
                        <span>未注册手机验证后自动登录，注册即代表同意
                            <span class="agreement">《知道吧协议》</span>
                            <span class="agreement">《隐私保护指引》</span>
                        </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {ArrowDown, Picture} from "@element-plus/icons-vue";
import QQ from "@/components/icons/QQ.vue";
import WeChat from "@/components/icons/WeChat.vue";
import Github from "@/components/icons/Github.vue";
import {http, http_no_token} from "@/utils/http/http";
import {ElMessage, ElMessageBox} from "element-plus";
import ValidationUtils from "@/utils/vaildation";

export default {
    //组件名
    name: "login",
    //依赖的组件
    components: {Github, WeChat, QQ, ArrowDown, Picture},
    //数据
    data() {
        return {
            loginForm: {
                phone: {
                    prefix: '86',
                    number: '',
                    label: '中国大陆'
                },
                captcha: ''

            },
            loginFormByPassword: {
                username: "",
                password: '',
            },
            registerForm: {
                username: '',
                password: ''
            },
            activeName: 'passwd',
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
            isLoading: false
        }
    },
    //方法
    methods: {
        login() {
            if (this.isLoading) return
            let validation = ValidationUtils.validate()
                .validateEmpty(this.loginFormByPassword.password, this.loginForm.phone)
                .validateUsername(this.loginFormByPassword.username)
                .validatePassword(this.loginFormByPassword.password)
            if (!validation.status) {
                ElMessage({
                    message: `登录失败 ：${validation.msg}`,
                    type: "error"
                })
                return;
            }

            this.isLoading = true
            http_no_token.post('/user/login', {
                username: this.loginFormByPassword.username,
                password: this.loginFormByPassword.password
            }).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: `登录成功！`,
                        type: "success"
                    })
                    this.$cookies.set('token', res.data.data.token)
                    this.$store.commit('SET_USER', res.data.data.userVo)
                    this.$store.commit('SET_LOGIN_STATE', true)
                    localStorage.setItem('userId', res.data.data.userVo.id)
                    this.$emit('login')

                } else {
                    ElMessage({
                        message: `登录失败：${res.data.msg},code:${res.data.code}`,
                        type: "error"
                    })
                }
            }, err => {
                console.log(err);
            }).finally(() => {
                this.isLoading = false
            })
        },
        loginBySms() {
            this.$popUp('尚未实现', '', 'info', 1000)
        },
        logout() {
            if (this.isLoading) return


        },
        register() {
            if (this.isLoading) return
            let validation =
                ValidationUtils.validate()
                    .validateEmpty(this.registerForm.username, this.registerForm.password)
                    .validateUsername(this.registerForm.username)
                    .validatePassword(this.registerForm.password)
            if (!validation.status) {
                ElMessage({
                    message: `注册失败 ：${validation.msg}`,
                    type: "error"
                })
                return;
            }

            this.isLoading = true
            http.post('user/register', {
                username: this.registerForm.username,
                password: this.registerForm.password
            }).then(res => {
                if (res.data.code === 200) {
                    ElMessage({
                        message: `注册成功：即将转入主页`,
                        type: "success"
                    })
                } else {
                    ElMessage({
                        message: `注册失败：${res.data.msg},code:${res.data.code}`,
                        type: "error"
                    })
                }
            }, err => {
                console.log(err);
            }).finally(() => {
                this.isLoading = false
            })

        }
    },
    //创建时执行
    created() {
        let isLogout = this.$route.query['isLogout']
        if (isLogout) {
            this.logout()
        }
    },
    //侦听器
    watch: {}
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
.login {
    display: grid;
    grid-template-columns: 2fr 3fr;
    grid-template-rows: 1fr;
    background: #fff;
    padding: 40px;
    grid-gap: 40px;
    transition: grid 0.5s;
    @media screen and (max-width: 850px) {
        grid-template-columns: 0fr 1fr;
        grid-gap: 0;
    }

    .qr {
        @include align(grid);
        min-width: 0;
        overflow: hidden;
        grid-template-rows: 1fr 2fr 1fr;

        .qr-body {
            .qrcode {
                max-width: 200px;
                width: 100%;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                object-fit: cover;
                transition: all 0.5s;
            }
        }

        .el-button {
            font-weight: lighter;
            font-size: 10px;
            padding: 5px;
            border-radius: 20px;
        }

        & > :not(.qr-footer) {
            @include align($fd: column);
        }
    }

    .login-form {
        @include align($fd: column, $jc: start, $ai: start);
        min-width: 0;

        .form-body {
            width: 100%;

            form {

                & > div {
                    @include align($jc: start, $ai: center);
                }

                .text-btn {
                    @include clearDefault();
                    @include clickEffect();
                    margin-left: auto;
                    color: $blue;
                    text-overflow: ellipsis;
                    white-space: nowrap;

                }

                .username, .captcha, .passwd {
                    padding-bottom: 5px;
                    border-bottom: 1px solid rgba(220, 223, 230, 0.51);

                    input {
                        @include clearDefault();
                        flex-grow: 1;
                        flex-basis: 5px;
                        height: 75px;
                    }

                    .phone-prefix {
                        @include clearDefault();
                        @include clickEffect();
                        align-self: stretch;
                        font-size: $fs-medium;
                        color: $blue;

                        .prefix-label {
                            @media screen and (max-width: 1000px) {
                                display: none;
                            }
                        }
                    }

                    @at-root .prefix-list {
                        height: 200px;
                        overflow-y: scroll;
                        overflow-x: hidden;
                        padding: 0;

                        @at-root .list-item {
                            @include clearDefault();
                            @include clickEffect($color: $white, $bg: $blue);
                            @include align($jc: start, $ai: center);
                            padding: 0;
                            height: 25px;
                            margin-bottom: 10px;

                        }
                    }
                }

                .pane-footer {
                    @include align($fd: column, $jc: start);
                    min-height: 35px;

                    & > * {
                        @include align();
                        margin-top: 10px;
                        text-align: right;
                        margin-left: auto;
                        width: 100%;
                    }

                    @at-root :deep(.el-progress__text) {
                        display: none !important;
                    }
                }
            }

        }

        .form-footer {
            & > * {
                @include align($jc: start, $ai: center);
            }

            @include align(grid, $jc: start, $ai: center);

            .other-way-icons {
                margin-top: 20px;
                margin-bottom: 20px;

                .el-icon {
                    flex-grow: 1;
                    flex-basis: 10px;
                    font-size: 50px;
                    cursor: pointer;
                }
            }

            .agreement {
                color: $dark-blue;
                @include clickEffect()
            }
        }

        .submit-btn {
            @include clickEffect();
            margin-top: 20px;

            button {
                height: 40px;
                background: #056de8;
                color: white;
                border: 1px solid #056de8;
                border-radius: 5px;

                &:hover {
                    background: #2b80e3;
                }
            }
        }

    }

    &:hover {
        box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.1);
    }


}


:deep(.el-tabs__nav-wrap::after) {
    width: 0;
}

/*改变tab文字大小*/
:deep(.el-tabs__item) {
    font-size: $fs-medium;
}


</style>
