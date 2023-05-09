<template>
    <div class="w-100 navbar fixed-top">
        <div class="nav-collapse">
            <el-icon>
                <navgator></navgator>
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
            <el-popover
                placement="bottom"
                trigger="click"
            >
                <ul class="list-group d-flex  align-content-center">
                    <router-link :to="`/home-user/${$store.state.userId}`">
                        <li class="list-group-item">
                            <el-icon>
                                <UserFilled/>
                            </el-icon>
                            <span>我的主页</span>
                        </li>
                    </router-link>

                    <li class="list-group-item">
                        <el-icon>
                            <BellFilled/>
                        </el-icon>
                        <span>消息</span>

                    </li>
                    <li class="list-group-item">
                        <el-icon>
                            <Comment/>
                        </el-icon>
                        <span>私信</span>
                    </li>
                    <li class="list-group-item">
                        <el-icon>
                            <SwitchButton/>
                        </el-icon>
                        <span>退出</span>
                    </li>
                </ul>
                <template #reference>
                    <img :src="this.$store.state.userInfo.avatar"
                         alt="avatar"/>
                </template>
            </el-popover>
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
import {Burger, HomeFilled, Message, More, Search} from "@element-plus/icons-vue";
import Messages from "@/components/nav/Messages.vue";
import Navgator from "@/components/icons/Navgator.vue";

export default {
    //组件名
    name: "home-navbar",
    //依赖的组件
    components: {More, Navgator, HomeFilled, Messages, Message, Search, Burger},
    //数据
    data() {
        return {
            tabs: ["主页", '热点', '会员', '发现'],
            currentTabIndex: 0,
            keyword: ''
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
        }

    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        // 默认为浅色主题
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
    width: 100%;
    padding: 0 50px;
    flex-wrap: nowrap;
    white-space: nowrap;
}

.nav-collapse {
    width: 60px;
    display: flex;
    justify-content: center;
    flex-grow: 0;
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
        justify-content: center;
        padding-left: 10px;
        padding-right: 10px;
    }

    .menu {
        display: none;
    }

    .search {
        display: none;
    }

    .logo {
        flex-grow: 1;
        display: flex;
        justify-content: center;
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
    /*蓝紫线性渐变*/
    color: #00070e !important;
    font-weight: bold !important;
}

.icons {
    margin-right: 15px;
    flex-grow: 0;
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
</style>
