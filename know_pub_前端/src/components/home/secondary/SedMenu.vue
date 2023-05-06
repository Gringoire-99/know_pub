<template>
    <div class="root d-flex flex-column w-100">
        <div class="mb-4">
            <el-carousel :interval="8000" arrow="never" class="w-100" height="100px">
                <el-carousel-item v-for="item in 3">
                    <img alt="" class="w-100 h-100" src="http://dummyimage.com/300x200">
                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="d-flex">
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <live></live>
                    </el-icon>
                    <span>直播</span>
                </div>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <shop></shop>
                    </el-icon>
                    <span>好物</span>
                </div>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <pen></pen>
                    </el-icon>
                    <span>专栏</span>
                </div>
            </div>
        </div>
        <div class="d-flex">
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <book></book>
                    </el-icon>
                    <span>百科</span>
                </div>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <question></question>
                    </el-icon>
                    <span>提问</span>
                </div>
            </div>
            <div class="d-flex flex-column flex-fill align-items-center justify-content-center pointer menu">
                <div class="menu-content">
                    <el-icon class="w-100">
                        <hot></hot>
                    </el-icon>
                    <span>热点</span>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import Live from "@/components/icons/Live.vue";
import Shop from "@/components/icons/Shop.vue";
import Pen from "@/components/icons/Pen.vue";
import Book from "@/components/icons/Book.vue";
import Question from "@/components/icons/Question.vue";
import Hot from "@/components/icons/Hot.vue";

export default {
    //组件名
    name: "sed-menu",
    //依赖的组件
    components: {Hot, Question, Book, Pen, Shop, Live},
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
.root {
    margin-bottom: 10px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border-radius: 1px;
    border: 1px solid #ebebeb;

}

.el-icon {
    font-size: 25px;
    margin-bottom: 10px;
}

span {
    font-size: 15px;
    color: rgba(120, 120, 121, 0.71);
}

.menu {
    margin-bottom: 15px;
}

.menu-content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 1 1 auto;
}
</style>
