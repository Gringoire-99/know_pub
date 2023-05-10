<template>
    <div class="w-100 navbar fixed-top">
        <div class="nav-collapse">
            <el-icon>
                <navigator></navigator>
            </el-icon>
        </div>
        <div class="nav-item logo">
            <img alt="logo" src="../../assets/common/logo_full.png">
        </div>
        <div class="nav-item menu">
            <button v-for="(tab,index) in tabs"
                    :key="index" :class="{'selected':index===currentTabIndex}" @click="onTabChange(index)">{{ tab }}
            </button>
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
        <div :class="{'hover':showAvatarDetail}" class="avatar" @mouseleave="hideDetail">
            <img :src="userInfo.avatar" alt="avatar"
                 @mouseenter="showDetail"/>
            <div class="avatar-dropdown">
                <span class="name">{{ userInfo.name }}</span>
                <div class="fs-xsm">
                    <span class="me-2"><span class="fw-lighter">K币：</span>{{ userInfo.koinCount }}</span>
                    <span><span class="fw-lighter">经验：</span>{{ userInfo.experienceCount }}</span>
                </div>
                <div>
                    <span class="fs-xsm text-primary">Lv1</span>
                    <el-progress :percentage="50" :show-text="false" class="flex-fill"/>
                    <span class="fs-xsm text-info">Lv2</span>
                </div>
                <div>
                    <span class="fs-xsm fw-lighter text-gray">{{
                            `当前经验${userInfo.experienceCount}，距离升级Lv1，还需要${12}`
                        }}</span>
                </div>
                <div class="mt-2 mb-2">
                    <div class="d-flex flex-column align-items-center flex-fill">
                        <span class="fw-bold">{{ userInfo.followCount }}</span>
                        <span class="fs-xsm">关注</span>
                    </div>
                    <div class="d-flex flex-column align-items-center flex-fill">
                        <span class="fw-bold">{{ userInfo.followerCount }}</span>
                        <span class="fs-xsm">粉丝</span>
                    </div>
                    <div class="d-flex flex-column align-items-center flex-fill">
                        <span class="fw-bold">{{ userInfo.articleCount + userInfo.postCount }}</span>
                        <span class="fs-xsm">动态</span>
                    </div>
                </div>
                <div class="member">
                    <div class="d-flex flex-column">
                        <span class="text-gold">成为会员</span>
                        <span class="text-white">了解更多权益</span>
                    </div>
                    <button class="member-button fs-xsm align-self-center ">开通</button>
                </div>
            </div>
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
    </div>
</template>

<script>
import {Avatar, Burger, HomeFilled, Message, More, Search} from "@element-plus/icons-vue";
import Messages from "@/components/nav/Messages.vue";
import Navigator from "@/components/icons/Navigator.vue";
import Mock from "mockjs";

export default {
    //组件名
    name: "home-navbar",
    //依赖的组件
    components: {Avatar, Navigator, More, HomeFilled, Messages, Message, Search, Burger},
    //数据
    data() {
        return {
            tabs: ["主页", '热点', '会员', '发现'],
            currentTabIndex: 0,
            keyword: '',
            showAvatarDetail: false,
            hideDelay: {},
            userInfo: {
                id: '',
                name: '',
                avatar: '',
                description: '',
                postCount: 0,
                articleCount: 0,
                followerCount: 0,

            },
            progress: 50
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
<style scoped>
.sub-nav {
    display: flex;
    justify-content: center;
    align-content: center;
    flex-grow: 2;
    flex-wrap: nowrap;

}

img[alt='avatar'] {
    align-self: center;
    background: white;
    width: 40px;
    height: 40px;
    border: 1px solid rgba(148, 150, 150, 0.27);
    border-radius: 5px;
}

.search {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: 4;
}

.search-btn {
    border: 1px solid #0095da;
    border-radius: 20px;
    background: #0095da;
    color: white;
    outline: none;
    padding: 3px 15px;
    height: 40px;
    margin-left: 20px;
}

.logo {
    flex-grow: 0;
}

.menu {
    flex-grow: 2;
    height: 100%;
    display: flex;
    justify-content: start;
    align-content: center;
}

.navbar {
    display: flex;
    justify-content: start;
    align-items: center;
    background-color: #ffffff;
    height: 60px;
    box-shadow: 0 0 10px #b9b4b4;
    padding: 0 50px;
    flex-wrap: nowrap;
    white-space: nowrap;
}

.nav-collapse {
    width: 60px;
    display: flex;
    justify-content: center;
    position: absolute;
    left: 10px;
}

.nav-item {
    box-sizing: content-box;
    margin-left: 10px;
    margin-right: 10px;
    cursor: pointer;
}

.menu > button {
    font-weight: lighter;
    color: #696969;
    border: none;
    background: none;
    padding-top: 15px;
    padding-bottom: 15px;
    margin-left: 10px;
    margin-right: 10px;
}


.menu span {
    margin-left: 20px;
}

img[alt="logo"] {
    height: 40px;
}

@media screen and (min-width: 648px) {
    .nav-collapse {
        display: none;
    }

    .icons {
        display: none !important;
    }
}

/*手机适配*/
@media screen and (max-width: 648px) {
    .navbar {
        padding: 0;
        justify-content: center;
    }

    .menu {
        display: none;
    }

    .search {
        display: none;
    }

    .logo {
        display: flex;
    }

    .sub-nav {
        flex-grow: 0;
    }

    .sub-nav * {
        display: none;
    }

    .sub-nav img {
        display: none;
    }

    .avatar {
        display: none !important;
    }

}

:deep(.el-autocomplete .el-input__wrapper) {
    height: 40px;
    border-radius: 20px;
}

/* Underline From Center */


.menu > button {
    transition-property: color, font-weight;
    transition-duration: 0.3s;
    display: inline-block;
    vertical-align: middle;
    -webkit-transform: perspective(1px) translateZ(0);
    transform: perspective(1px) translateZ(0);
    box-shadow: 0 0 1px rgba(0, 0, 0, 0);
    position: relative;
    overflow: hidden;
}

.menu > button:before {
    content: "";
    position: absolute;
    z-index: -1;
    left: 51%;
    right: 51%;
    bottom: 0;
    background: #2098D1;
    height: 4px;
    -webkit-transition-property: left, right;
    transition-property: left, right;
    -webkit-transition-duration: 0.1s;
    transition-duration: 0.1s;
    -webkit-transition-timing-function: ease-out;
    transition-timing-function: ease-out;
}

.menu > button:hover:before, .menu > button:focus:before, .menu > button:active:before {
    left: 0;
    right: 0;
}

.selected:before {
    left: 0 !important;
    right: 0 !important;
}

.selected {
    color: #00070e !important;
    font-weight: bold !important;
}

.icons {
    margin-right: 15px;
    position: absolute;
    right: 10px;
}

.search-icon {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-content: center;
    width: 30px;
    height: 30px;
    border: 1px solid #073348;
    border-radius: 50%;
}

.etc {
    margin-left: 10px;
    display: flex;
    justify-content: center;
    align-content: center;
    flex-wrap: wrap;
}

.sub-nav {
    margin-left: 10px;
}

.sub-nav .el-button {
    border: none;
}

.sub-nav .el-button .el-icon {
    font-size: 20px;
}

img[alt='avatar'] {
    border: 1px solid white;
    border-radius: 5px;
    cursor: pointer;
    transition-property: all;
    transition-duration: 0.5s;
    box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px, rgba(17, 17, 26, 0.1) 0px 0px 8px;
}

.avatar {
    right: 20px;
    position: absolute;
    transition-property: all;
    transition-duration: 0.3s;
    z-index: 1;
    display: flex;
    justify-content: center;
    align-content: center;
}

.avatar-dropdown {
    height: 250px;
    width: 270px;
    position: absolute;
    background-color: white;
    padding-top: 40px;
    top: 20px;
    opacity: 0;
    transition: all 1s;
    z-index: -1;
    display: flex;
    flex-direction: column;
    padding-right: 20px;
    padding-left: 20px;
}

.avatar-dropdown > * {
    width: 100%;
    display: flex;
    justify-content: center;
    align-content: center;
    flex-wrap: wrap;
}

.hover .avatar-dropdown {
    height: 300px;
    display: flex;
    opacity: 1;
    box-shadow: rgba(136, 165, 191, 0.48) 6px 2px 16px 0px, rgba(255, 255, 255, 0.8) -6px -2px 16px 0px;
}

.hover img[alt='avatar'] {
    position: relative;
    border-radius: 50%;
    z-index: 2;
    transform: scale(2);
    box-shadow: rgb(204, 219, 232) 3px 3px 6px 0px inset, rgba(255, 255, 255, 0.5) -3px -3px 6px 1px inset;

}

.hover {
    transform: translate(-100px, 30px);
}

.member-button {
    border: none;
    background: white;
    color: rgba(0, 85, 213, 1);
    margin-left: auto;
    width: fit-content;
    height: fit-content;
    border-radius: 5px;
    padding: 5px 10px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
    font-weight: bold;

}

.member {
    padding: 10px;
    border-radius: 5px;
    background: url('../../assets/bg/membership_bg.png') no-repeat center;
    background-size: cover;
}

.member span {
    font-weight: bold;
    font-size: 10px;
}
</style>
