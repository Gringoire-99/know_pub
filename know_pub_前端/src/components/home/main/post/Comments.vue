<template>
    <div>

    </div>
</template>

<script>
import http from "@/utils/http/http";

export default {
    //组件名
    name: "comments",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            pageSize: 10,
            pageIndex: 0,
            isLoading: false,
            comments: []

        }
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        http.get('/comments', {
            params: {
                postId: this.postId,
                pageSize: this.pageSize,
                pageIndex: this.pageIndex
            }
        }).then(
            resolve => {
                if (resolve.status === 200) {
                    // 连接两个数组
                    this.comments.push(...resolve.data)
                    this.isLoading = false

                    console.log(this.comments)
                } else {
                    alert("failed")
                }
            }, reason => {
                alert("failed")
            }
        )
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
        postId: {
            type: String,
            required: true
        },
        // 是否是完整的评论列表，否：只加载10条，没有无限加载方法，是：划到底自动加载
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
