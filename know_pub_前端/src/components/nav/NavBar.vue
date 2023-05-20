<template>
    <div class="w-100 navbar fixed-top">
        <div class="nav-collapse">
            <el-icon>
                <navigator></navigator>
            </el-icon>
        </div>
        <div class=" logo d-flex align-items-center">
            <img alt="logo" src="../../assets/common/logo_full.png">
        </div>
        <div class="d-flex nav-items">
            <div class="nav-item">
                <router-link v-for="(tab,index) in tabs" :to="tab.path" active-class="selected">
                    <button :key="index" @click="onTabChange(index)">
                        {{ tab.label }}
                    </button>
                </router-link>

            </div>
        </div>
        <div class="search">
            <el-autocomplete
                v-model="keyword"
                :fetch-suggestions="querySearch"
                :popper-append-to-body='false'
                class="search-input w-75"
                clearable
                placeholder="想知道什么？"
                @select="handleSearchSelect"
            >
                <template #suffix>
                    <el-icon>
                        <Search/>
                    </el-icon>
                </template>
                <template #default="{ item }">
                    <div class="value">{{ item.value }}</div>
                    <span class="link">{{ item.link }}</span>
                </template>
            </el-autocomplete>
            <button class="search-btn" type="button" @click="handleSearch">提问</button>
        </div>
        <div class="icons d-flex align-content-center justify-content-center">
            <div class="search-icon">
                <el-icon>
                    <search></search>
                </el-icon>
            </div>
            <div class="etc">
                <el-icon>
                    <more></more>
                </el-icon>
            </div>
        </div>
        <div class="grid-avatar">
            <div class="sub-nav">
                <el-popover :width="400" placement="bottom" trigger="click">
                    <template #reference>
                        <el-button class="ms-3 h-100">
                            <div>
                                <el-icon>
                                    <Bell/>
                                </el-icon>
                                <br>
                                <span>消息</span>
                            </div>
                        </el-button>
                    </template>
                    <messages></messages>
                </el-popover>
                <el-popover :width="400" placement="bottom" trigger="click">
                    <template #reference>
                        <el-button class="h-100">
                            <div>
                                <el-badge type="danger" value="1">
                                    <el-icon>
                                        <message/>
                                    </el-icon>
                                </el-badge>
                                <br>
                                <span>私信</span>
                            </div>
                        </el-button>
                    </template>
                </el-popover>
            </div>
            <div v-if="$store.state.isLogin" :class="{'hover':showAvatarDetail}" class="avatar" @mouseenter="clearTimer"
                 @mouseleave="hideDetail">
                <img :src="userInfo.avatar" alt="avatar"
                     @mouseenter="showDetail"/>
                <div class="avatar-dropdown">
                    <span class="name pointer">{{ userInfo.name }}</span>
                    <div class="fs-xsm">
                        <span class="me-2"><span class="fw-lighter">K币：</span>{{ userInfo.koinCount }}</span>
                        <span><span class="fw-lighter">经验：</span>{{ userInfo.experienceCount }}</span>
                    </div>
                    <div>
                        <span class="fs-xsm text-primary pointer">Lv1</span>
                        <el-progress :percentage="50" :show-text="false" class="flex-fill"/>
                        <span class="fs-xsm text-info pointer">Lv2</span>
                    </div>
                    <!--                TODO 通过js计算等级-->
                    <div>
                    <span class="fs-xsm fw-lighter text-gray">{{
                            `当前经验${userInfo.experienceCount}，距离升级Lv1，还需要${12}`
                        }}</span>
                    </div>
                    <div class="mt-2 mb-2">
                        <div class="d-flex flex-column align-items-center flex-fill ">
                            <span class="fw-bold pointer">{{ userInfo.followCount }}</span>
                            <span class="fs-xsm pointer">关注</span>
                        </div>
                        <div class="d-flex flex-column align-items-center flex-fill pointer">
                            <span class="fw-bold pointer">{{ userInfo.followerCount }}</span>
                            <span class="fs-xsm pointer">粉丝</span>
                        </div>
                        <div class="d-flex flex-column align-items-center flex-fill pointer">
                            <span class="fw-bold pointer">{{ userInfo.articleCount + userInfo.postCount }}</span>
                            <span class="fs-xsm pointer">动态</span>
                        </div>
                    </div>
                    <div class="member">
                        <div class="d-flex flex-column">
                            <span class="text-red pointer">成为会员</span>
                            <span class="text-white pointer">了解更多权益</span>
                        </div>
                        <button class="member-button fs-xsm align-self-center">开通</button>
                    </div>
                    <div class="avatar-nav-item w-100 mt-1">
                        <div class="w-100">
                            <router-link :to="`/home-user/${$store.state.userId}`" active-class="selected">
                                <div class="d-flex align-items-center nav-list-item">
                                    <el-icon class="me-3">
                                        <House/>
                                    </el-icon>
                                    <span class="">个人中心</span>
                                    <el-icon class="ms-auto">
                                        <arrow-right-bold></arrow-right-bold>
                                    </el-icon>
                                </div>
                            </router-link>
                            <router-link active-class="selected" to="/home-user">
                                <div class="d-flex align-items-center nav-list-item">
                                    <el-icon class="me-3">
                                        <wallet></wallet>
                                    </el-icon>
                                    <span class="">我的钱包</span>
                                    <el-icon class="ms-auto">
                                        <arrow-right-bold></arrow-right-bold>
                                    </el-icon>
                                </div>
                            </router-link>
                            <router-link active-class="selected" to="/home-user">
                                <div class="d-flex align-items-center nav-list-item">
                                    <el-icon class="me-3">
                                        <setting></setting>
                                    </el-icon>
                                    <span class="">我的设置</span>
                                    <el-icon class="ms-auto">
                                        <arrow-right-bold></arrow-right-bold>
                                    </el-icon>
                                </div>
                            </router-link>
                            <div class="el-divider--horizontal mt-1 mb-1"></div>
                            <router-link :to="{name:'login-page',query:{isLogout: true}}" active-class="selected">
                                <div class="d-flex align-items-center nav-list-item">
                                    <el-icon class="me-3">
                                        <House/>
                                    </el-icon>
                                    <span class="">退出登录</span>
                                </div>
                            </router-link>
                        </div>

                    </div>

                </div>
            </div>
            <div v-else class="login-btn">
                <span class="text-blue pointer" @click="isShowLogin=!isShowLogin">登录</span>
            </div>
        </div>

    </div>
    <el-dialog v-model="isShowLogin" center class="dialog-login">
        <login/>
    </el-dialog>
</template>

<script>
import {
    ArrowRight,
    ArrowRightBold,
    Avatar,
    Burger,
    HomeFilled,
    Message,
    More,
    Search, Setting,
    Wallet
} from "@element-plus/icons-vue";
import Messages from "@/components/nav/Messages.vue";
import Navigator from "@/components/icons/Navigator.vue";
import Mock from "mockjs";
import Login from "@/components/common/Login.vue";

export default {
    //组件名
    name: "home-navbar",
    //依赖的组件
    components: {
        Login,
        Setting,
        Wallet,
        ArrowRightBold, ArrowRight, Avatar, Navigator, More, HomeFilled, Messages, Message, Search, Burger
    },
    //数据
    data() {
        return {
            tabs: [
                {label: '主页', path: '/home-main'},
                {label: '热点', path: '/home-hot'},
                {label: '会员', path: '/home-member'},
                {label: '发现', path: '/home-discover'},
            ],
            currentTabIndex: 0,
            keyword: '',
            showAvatarDetail: false,
            hideDelay: {},
            progress: 50,
            isShowLogin: false,

        }
    },
    //方法
    methods: {
        // 搜索框自动补全,queryString为输入的关键字,cb为回调函数,cb(data)为返回的数据
        querySearch(queryString, cb) {
            cb([{value: "123", link: "123"}, {value: "123", link: "123"}])
        },
        handleSearchSelect() {

        },
        search() {
            // TODO 跳转到搜索页面，传入搜索关键字，搜索结果页面
            // this.$router.push({path: '/search', query: {keyword: this.keyword}});
        },
        onTabChange(index) {

            this.currentTabIndex = index
        },
        handleSearch() {
        },
        showDetail() {
            if (this.hideDelay) {
                clearTimeout(this.hideDelay)
            }
            this.showAvatarDetail = true

        },
        hideDetail() {
            this.hideDelay = setTimeout(() => {
                this.showAvatarDetail = false
            }, 1000)
        },
        clearTimer() {
            clearTimeout(this.hideDelay)
        }


    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {

    },
    //侦听器
    watch: {
        $store: {
            handler() {
                this.userInfo = this.$store.state.userInfo
            },
            deep: true
        }
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


.navbar {
    display: grid;
    grid-template-columns: 0fr 1fr 4fr 4fr 0fr 2fr;
    grid-template-rows: 1fr;
    background-color: #ffffff;
    height: 60px;
    box-shadow: 0 0 10px #b9b4b4;
    padding: 0 50px;
    transition: all 0.5s;

    .selected {
        * {
            font-weight: bold !important;
            color: $dark-blue !important;
        }
    }

    & > div {
        min-width: 0;
        overflow: hidden;
        opacity: 1;
    }


    .nav-collapse {

    }

    .logo {
        @include align($jc: start);
        transition: all 0.3s;

        img {
            height: 40px;
        }
    }

    .nav-items {
        .nav-item {
            @include align($jc: start);
            @include clickable();
            flex-grow: 2;
            height: 100%;
            white-space: nowrap;


            button {
                @include clearDefault();
                font-weight: lighter;
                color: $gray;
                border: none;
                background: none;
                padding-top: 15px;
                padding-bottom: 15px;
                margin-left: 10px;
                margin-right: 10px;
                @include hoverUnderLine(center, 0.1s, lighten($dark-blue, 20%));
            }

            span {
                margin-left: 20px;
            }
        }
    }

    .search {
        @include align();
        flex-grow: 4;

        :deep(.el-autocomplete .el-input__wrapper) {
            height: 40px;
            border-radius: 20px;
        }

        .search-btn {
            border: 1px solid $blue;
            border-radius: 20px;
            background: $blue;
            color: white;
            outline: none;
            padding: 3px 15px;
            height: 40px;
            margin-left: 20px;
            white-space: nowrap;
            @include clickEffect($white);
        }
    }


    .icons {
        .search-icon {
            @include align();
            width: 30px;
            height: 30px;
            border: 1px solid $dark-blue;
            border-radius: 50%;
        }

        .etc {
            margin-left: 10px;
            @include align()
        }
    }

    .grid-avatar {
        .sub-nav {
            margin-left: 10px;
            @include align();

            .el-button {
                border: none;

                .el-icon {
                    font-size: 20px;
                }
            }
        }

        .avatar-nav-item {

            .nav-list-item {
                padding: 10px 20px;
                color: $dark-blue;
                border-radius: 5px;
                background-color: $white;
                transition: background-color 0.5s;
                font-size: 15px;

                &:hover {
                    background-color: rgba(126, 131, 131, 0.15);
                }
            }

            * {
                text-decoration: none;
            }
        }

        .avatar {
            top: 10px;
            right: 20px;
            position: absolute;
            transition-property: all;
            transition-duration: 0.3s;
            z-index: 1;
            display: flex;
            justify-content: center;
            align-content: center;
            transition-delay: 0.5s;

            img[alt='avatar'] {
                align-self: center;
                background: white;
                width: 40px;
                height: 40px;
                border: 1px solid rgba(148, 150, 150, 0.27);
                border-radius: 5px;
                cursor: pointer;
                transition-property: all;
                transition-duration: 0.5s;
                box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px, rgba(17, 17, 26, 0.1) 0px 0px 8px;
                transition-delay: 0.3s;

            }

            .avatar-dropdown {
                height: 100px;
                width: 270px;
                position: absolute;
                background-color: white;
                padding-top: 40px;
                top: 20px;
                opacity: 0;
                transition: height 0.2s, opacity 0.5s;
                z-index: -1;
                display: flex;
                flex-direction: column;
                padding-right: 20px;
                padding-left: 20px;
                overflow: hidden;

                & > * {
                    width: 100%;
                    @include align();
                }

                .member {
                    padding: 10px;
                    border-radius: 5px;
                    background: url('../../assets/bg/membership_bg.png') no-repeat center;
                    background-size: cover;

                    .member-button {
                        border: none;
                        background: white;
                        color: $dark-blue;
                        margin-left: auto;
                        width: fit-content;
                        height: fit-content;
                        border-radius: 5px;
                        padding: 5px 10px;
                        box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
                        font-weight: bold;

                    }

                    span {
                        font-weight: bold;
                        font-size: 10px;
                        cursor: pointer;
                    }
                }
            }


            &.hover {
                transform: translate(-100px, 30px);

                .avatar-dropdown {
                    height: 420px;
                    display: flex;
                    opacity: 1;
                    box-shadow: rgba(136, 165, 191, 0.48) 6px 2px 16px 0px, rgba(255, 255, 255, 0.8) -6px -2px 16px 0px;
                    transition-delay: 0.5s;
                }

                img[alt='avatar'] {
                    position: relative;
                    border-radius: 50%;
                    z-index: 2;
                    transform: scale(2);
                    box-shadow: rgb(204, 219, 232) 3px 3px 6px 0px inset, rgba(255, 255, 255, 0.5) -3px -3px 6px 1px inset;
                    transition-delay: 0.3s;

                }
            }


        }
    }

    .login-btn {
        position: absolute;
        right: 10px;
        top: 20px;
        transition: opacity 0.5s;
    }


    :is(.nav-item,.search) {
        transition: opacity 0.5s;
        opacity: 1;
    }

    :is(.nav-collapse,.icons) {
        opacity: 0;
        transition: opacity 0.5s;
    }

    @media screen and (max-width: 850px) {
        grid-template-columns: 1fr 3fr 0fr 0fr 1fr 0fr;
        padding-right: 10px;
        padding-left: 10px;
        .logo {
            justify-content: center;
        }

        .avatar {
            display: none !important;
        }

        :is(.nav-collapse,.icons) {
            opacity: 1 !important;
        }

        :is(.nav-item,.search,.login-btn) {
            opacity: 0 !important;
        }
    }
}


</style>
