<template>
    <div>

    </div>
</template>

<script>
export default {
    //组件名
    name: "comments",
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
        // 评论列表
        comments: {
            type: Array,
            default: () => [],
            required: true
        },
        // 是否是完整的评论列表
        isComplete: {
            type: Boolean,
            default: false,
            required: true
        }
    }
}
</script>

<style scoped>

</style>
