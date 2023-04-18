<template>
    <div>
        <el-row :gutter="2" :class={navBar:true,darkTheme:darkTheme} >
            <el-col :span="10">
                <el-menu
                    mode="horizontal"
                    :router="true"
                    :active-text-color="menuStyle.dark.activeTextColor"
                    :background-color="menuStyle.dark.backgroundColor"
                    :text-color="menuStyle.dark.textColor"
                >
                    <el-menu-item index="icon" v-on:select="">知道吧icon
                        <el-icon>
                            <edit></edit>
                        </el-icon>
                    </el-menu-item>
                    <el-menu-item index="homeMain" v-on:select="">首页
                    </el-menu-item>
                    <el-sub-menu index="2">
                        <template #title>发现</template>
                        <el-menu-item index="hotSpots">头条</el-menu-item>
                        <el-menu-item index="2-2">1</el-menu-item>
                        <el-menu-item index="2-3">item three</el-menu-item>
                        <el-sub-menu index="2-4">
                            <template #title>item four</template>
                            <el-menu-item index="2-4-1">item one</el-menu-item>
                            <el-menu-item index="2-4-2">item two</el-menu-item>
                            <el-menu-item index="2-4-3">item three</el-menu-item>
                        </el-sub-menu>
                    </el-sub-menu>
                    <el-menu-item index="3" disabled>Info</el-menu-item>
                    <el-menu-item index="4">Oxrders</el-menu-item>
                </el-menu>
            </el-col>
            <el-col :span="10">
                <div class="search">
                    <el-autocomplete
                        :popper-append-to-body='false'
                        v-model="keyword"
                        :fetch-suggestions="querySearch"
                        clearable
                        class="search-input"
                        placeholder="Please Input"
                        @select="handleSearchSelect"
                    />
                </div>
            </el-col>
            <el-col :span="4"></el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    //组件名
    name: "home-navbar",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            darkTheme:true,
            keyword:'',
            menuStyle:{
                light:{
                    'activeTextColor':'#0095da',
                    'backgroundColor':'#FFFFFF',
                    'textColor':'#000000'
                },
                dark:{
                    'activeTextColor':'#b4deef',
                    'backgroundColor':'#012357',
                    'textColor':'#fdfdfd'
                }
            }

        }
    },
    //方法
    methods: {
        querySearch(){

        },
        handleSearchSelect(){

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
.darkTheme{
    background-color: #012357;
}
.darkTheme :deep(.el-autocomplete .el-input__wrapper){
    background-color: rgba(39, 72, 119, 0.35);
}
.navbar{

}
.menu{
    border: none;
    padding-top: 5px;
}
:deep(.el-autocomplete .el-input__wrapper){
    margin-top: 10px;
    /*    圆角搜索框*/
    border-radius: 20px;
    border: 1px solid #dcdfe6;
    background-color: #f5f7fa;
    width: 400px;

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
</style>
