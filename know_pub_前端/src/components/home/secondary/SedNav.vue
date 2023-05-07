<template>
    <div class="root">
        <div class="li">
            <el-icon>
                <user-filled></user-filled>
            </el-icon>
            <span>我的主页</span>
        </div>
        <div class="li">
            <el-icon>
                <star-filled></star-filled>
            </el-icon>
            <span>我的收藏</span>
        </div>
        <div class="li">
            <el-icon>
                <heart-filled></heart-filled>
            </el-icon>
            <span>我的关注</span>
        </div>


    </div>
</template>

<script>
import {ChromeFilled, StarFilled, UserFilled} from "@element-plus/icons-vue";
import Question from "@/components/icons/Question.vue";
import Topic from "@/components/icons/Topic.vue";
import HeartFilled from "@/components/icons/HeartFilled.vue";

export default {
    //组件名
    name: "sed-nav",
    //依赖的组件
    components: {HeartFilled, ChromeFilled, UserFilled, Topic, Question, StarFilled},
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
    display: flex;
    flex-direction: column;
}

.li .el-icon {
    font-size: 15px;
    margin-right: 10px;
    color: rgba(141, 142, 143, 0.98);

}

.li {
    padding: 20px;
    display: flex;
    align-items: center;
    cursor: pointer;
    font-size: 15px;
    color: rgba(141, 142, 143, 0.98);
}

.li:hover {
    background-color: rgba(0, 0, 0, 0.05);
}
</style>
