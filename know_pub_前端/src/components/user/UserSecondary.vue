<template>
    <div class="d-flex flex-column sed-root">
        <sed-write></sed-write>
        <div class="d-flex count align-content-center justify-content-center">
            <div class="d-flex flex-column  align-content-center justify-content-center flex-wrap flex-fill">
                <span class="text-gray fs-xsm ">关注了</span>
                <span class="fw-bold align-self-center">{{ userInfo.followCount }}</span>
            </div>
            <div class="d-flex flex-column  align-content-center justify-content-center  flex-wrap flex-fill">
                <span class="text-gray fs-xsm">关注者</span>
                <span class="fw-bold align-self-center">{{ userInfo.followerCount }}</span>
            </div>
        </div>
        <div class="d-flex flex-column nav flex-fill">
            <div><span>关注的话题</span><span class="ms-auto">4</span></div>
            <div><span>关注的问题</span><span class="ms-auto">1</span></div>
            <div><span>关注的收藏夹</span><span class="ms-auto">10</span></div>
            <div><span>关注的人</span><span class="ms-auto">0</span></div>
            <div class="mt-3 border-0"><span>个人主页被浏览228次</span></div>

        </div>
    </div>
</template>

<script>
import SedWrite from "@/components/home/secondary/SedWrite.vue";

export default {
    //组件名
    name: "user-secondary",
    //依赖的组件
    components: {SedWrite},
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
    props: {
        userInfo: {
            type: Object,
            require: true
        }
    }
}
</script>

<style scoped>
.count {
    padding-top: 20px;
    padding-bottom: 20px;
    box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

.count > div {
    border-right: 1px solid rgba(211, 211, 211, 0.6);
}

.count span {
    cursor: pointer;
}

.nav span {

    font-size: 10px;
    font-weight: lighter;
    cursor: pointer;

}

.nav div {
    padding-top: 10px;
    padding-bottom: 10px;
    display: flex;
    border-bottom: 1px solid rgba(211, 211, 211, 0.6);
}

.nav {
    margin-top: 10px;
    padding-right: 5px;
    padding-left: 5px;
}

.sed-root {
    max-width: 300px;
}
</style>
