<template>
    <div class="d-flex justify-content-start align-items-start w-100">
        <div class="avatar">
            <el-avatar :src="comments.rootComment.avatar" shape="square" size="small"></el-avatar>
        </div>
        <div class="d-flex flex-column w-100">
            <div class="d-flex align-items-center justify-content-start">
                <span class="fw-bold">{{ comments.rootComment.name }}</span>
                <el-icon class="more">
                    <MoreFilled/>
                </el-icon>
            </div>
            <span>{{ comments.rootComment.content }}</span>
            <div class="footer">{{ comments.rootComment.publishTime.substring(0, 10) }}</div>
        </div>
    </div>
</template>

<script>
export default {
    //组件名
    name: "root-comment",
    //依赖的组件
    components: {},
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
        console.log(this.comments)
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
        comments: {
            rootComment: {},
            childComments: [],
            type: Object,
            require: true
        }
    }
}
</script>

<style scoped>
.more {
    margin-left: auto;
}
</style>
