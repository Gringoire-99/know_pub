<template>
    <div class="root">
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
        <div class="li">
            <el-icon>
                <question></question>
            </el-icon>
            <span>我的关注的主题</span>
        </div>
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
    </div>
</template>

<script>
import {StarFilled} from "@element-plus/icons-vue";
import Question from "@/components/icons/Question.vue";

export default {
    //组件名
    name: "sed-nav",
    //依赖的组件
    components: {Question, StarFilled},
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
    padding: 20px;
    display: flex;
    flex-direction: column;
}

.li .el-icon {
    font-size: 25px;
    margin-right: 10px;
    color: rgba(141, 142, 143, 0.98);
}

.li {
    display: flex;
    align-items: center;
    font-size: 20px;
    color: rgba(141, 142, 143, 0.98);
}

</style>
