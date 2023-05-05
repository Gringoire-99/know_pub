<template>
    <div class="root d-flex align-items-center justify-content-center w-100">
        <post-card :id="'1'"></post-card>
    </div>
</template>

<script>
import PostBody from "@/components/home/main/post/PostBody.vue";
import PostCard from "@/components/user/PostCard.vue";

export default {
    //组件名
    name: "test",
    //依赖的组件
    components: {PostCard, PostBody},
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
    height: 100vh;
}
</style>
