<template>
    <div class="info-short d-flex flex-column">
        <div class="">
            <el-icon>
                <gender></gender>
            </el-icon>
            <div>
                性别
                <!--                                {{ userInfo.gender }}-->
            </div>
        </div>
        <div class="">
            <el-icon>
                <job></job>
            </el-icon>
            <div>
                职业
                <!--                                {{ userInfo.jobHistory }}-->
            </div>
        </div>
    </div>
</template>

<script>
import PostBody from "@/components/home/main/post/PostBody.vue";
import PostCard from "@/components/user/PostCard.vue";
import Job from "@/components/icons/Job.vue";
import Gender from "@/components/icons/Gender.vue";

export default {
    //组件名
    name: "test",
    //依赖的组件
    components: {Gender, Job, PostCard, PostBody},
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
.info-short > div {
    display: flex;
    align-content: center;
    flex-wrap: wrap;
}
</style>
