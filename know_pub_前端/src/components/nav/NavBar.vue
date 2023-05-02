<template xmlns="http://www.w3.org/1999/html">
    <div>
        <el-row :class={darkTheme:darkTheme} :gutter="2" class="navBar align-items-center">
            <el-col :lg="10" :md="10" :sm="5" :xl="10" :xs="2">
                <el-menu
                    :active-text-color="menuStyle.activeTextColor"
                    :background-color="menuStyle.backgroundColor"
                    :router="true"
                    :text-color="menuStyle.textColor"
                    mode="horizontal"
                >
                    <el-menu-item index="/home-main" v-on:select="">知道吧icon
                        <el-icon>
                            <edit></edit>
                        </el-icon>
                    </el-menu-item>
                    <el-menu-item index="/home-main" v-on:select="">首页
                    </el-menu-item>
                    <el-sub-menu index="2">
                        <template #title>发现</template>
                        <el-menu-item index="hot-spots">头条</el-menu-item>
                        <el-menu-item index="2-2">1</el-menu-item>
                        <el-menu-item index="2-3">item three</el-menu-item>
                        <el-sub-menu index="2-4">
                            <template #title>item four</template>
                            <el-menu-item index="2-4-1">item one</el-menu-item>
                            <el-menu-item index="2-4-2">item two</el-menu-item>
                            <el-menu-item index="2-4-3">item three</el-menu-item>
                        </el-sub-menu>
                    </el-sub-menu>
                    <el-menu-item disabled index="3">Info</el-menu-item>
                    <el-menu-item @click="getDarkTheme">黑暗模式</el-menu-item>
                </el-menu>
            </el-col>
            <el-col :lg="10" :md="10" :sm="10" :xl="10" :xs="12" class="search">
                <div class=" d-none d-md-flex justify-content-center">
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
                    <el-button class="publishButton d-flex" type="primary">
                        发布
                    </el-button>
                </div>

                <el-button circle class="d-inline d-md-none">
                    <el-icon>
                        <search></search>
                    </el-icon>
                </el-button>

            </el-col>
            <el-col :lg="4" :md="4" :sm="9" :xl="4" :xs="10" class="userInfo d-flex justify-content-center">
                <el-popover :width="400" placement="bottom" trigger="click">
                    <template #reference>
                        <el-button>
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
                        <el-button class="">
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
                <el-popover
                    placement="bottom"
                    trigger="click"
                >
                    <ul class="list-group">
                        <li class="list-group-item">
                            <el-icon>
                                <HomeFilled></HomeFilled>
                            </el-icon>
                            我的主页
                        </li>
                        <li class="list-group-item">
                            <el-icon>
                                <HomeFilled></HomeFilled>
                            </el-icon>
                            我的主页
                        </li>
                        <li class="list-group-item">
                            <el-icon>
                                <HomeFilled></HomeFilled>
                            </el-icon>
                            我的主页
                        </li>
                        <li class="list-group-item">
                            <el-icon>
                                <HomeFilled></HomeFilled>
                            </el-icon>
                            我的主页
                        </li>
                    </ul>
                    <template #reference>
                        <el-button>
                            <el-avatar :src="this.$store.state.userInfo.avatar"
                                       alt="" shape="square"/>
                        </el-button>
                    </template>
                </el-popover>

            </el-col>
        </el-row>

    </div>
</template>

<script>
import {Burger, HomeFilled, Message, Search} from "@element-plus/icons-vue";
import Messages from "@/components/nav/Messages.vue";

export default {
    //组件名
    name: "home-navbar",
    //依赖的组件
    components: {HomeFilled, Messages, Message, Search, Burger},
    //数据
    data() {
        return {
            darkTheme: false,
            keyword: '',
            style: {
                light: {
                    'activeTextColor': '#0095da',
                    'backgroundColor': '#FFFFFF',
                    'textColor': '#000000'
                },
                dark: {
                    'activeTextColor': '#b4deef',
                    'backgroundColor': '#012357',
                    'textColor': '#fdfdfd'
                },
            },
            menuStyle: {}


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
        getDarkTheme() {
            this.darkTheme = !this.darkTheme;
            this.menuStyle = this.darkTheme ? this.style.dark : this.style.light;
        },
        search() {
            // TODO 跳转到搜索页面，传入搜索关键字，搜索结果页面
            // this.$router.push({path: '/search', query: {keyword: this.keyword}});
        },

    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        // 默认为浅色主题
        this.menuStyle = this.style.light;
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

.list-group {
    padding: 0;
    margin: 0;
    border: none;
}

.list-group-item {
    border: none;
}

.list-group-item:hover {
    background: #f5f5f5;
}


.navBar {
    background-color: #ffffff;
}

.userInfo .el-button {
    height: 50px;
    border: none;
    margin-left: 0;
}

.darkTheme.navBar {
    color: white;
    background-color: #012357;
}

:deep(.el-sub-menu__title) {
    margin: 0;
    padding: 0;
}

.darkTheme .el-col .publishButton {
    background-color: #dcdfe6;
    color: #0776c7;
}

.darkTheme :deep(.el-autocomplete .el-input__wrapper) {
    background-color: rgba(39, 72, 119, 0.35);
}

.darkTheme :deep(.userInfo .el-button ) {
    background-color: #012357;
    color: white;
}

:deep(.navBar) {
    border-bottom: 1px solid #dcdfe6;
}

:deep(.el-menu) {
    border: none;
}

.userInfo .el-icon {
    font-size: 22px;
}


:deep(.el-autocomplete .el-input__wrapper) {
    /*    圆角搜索框*/
    border-radius: 20px;
    border: 1px solid #dcdfe6;
    background-color: #f5f7fa;
    height: 40px;
}

.el-icon {
    cursor: pointer;
}

/* Underline From Center */
.el-menu-item {
    display: inline-block;
    vertical-align: middle;
    -webkit-transform: perspective(1px) translateZ(0);
    transform: perspective(1px) translateZ(0);
    box-shadow: 0 0 1px rgba(0, 0, 0, 0);
    position: relative;
    overflow: hidden;
}

.el-menu-item:before {
    content: "";
    position: absolute;
    z-index: -1;
    left: 51%;
    right: 51%;
    bottom: 0;
    background: #0095da;
    height: 4px;
    -webkit-transition-property: left, right;
    transition-property: left, right;
    -webkit-transition-duration: 0.3s;
    transition-duration: 0.1s;
    -webkit-transition-timing-function: ease-out;
    transition-timing-function: ease-out;
}

.el-menu-item:hover:before, .el-menu-item:focus:before, .el-menu-item:active:before {
    left: 0;
    right: 0;
}

.publishButton {
    /*    圆边按钮*/
    border-radius: 20px;
    background-color: #0278ff;
    color: #FFFFFF;
    height: 40px;
    width: 70px;
    margin-left: 10px;

}
</style>
