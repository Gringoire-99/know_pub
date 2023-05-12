<template>
    <div>
        1
        <div class="header w-100">
            <div class="d-flex flex-column">
                <div class="tags">
                    <el-tag v-for="tag in question.tags"  size="small" type="success" class="m-1">
                        {{ tag.name }}
                    </el-tag>
                </div>
            </div>
        </div>
        <div class="answers">

        </div>
    </div>
</template>

<script>
import http from "@/utils/http/http";

export default {
    //组件名
    name: "home-question",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            question: {
                questionId: '',
                title: '',
                content: '',
                likeCount: '',
                commentCount: '',
                answerCount: '',
                tags: {name:'',id:''}
            },
            posts: [],
            //方法
        }
    },
    methods: {},
    //挂载时执行
    mounted() {
        console.log("mounted")
    }
    ,
    //创建时执行
    created() {
        console.log("created")
        http.get('/question', {
            params: {
                questionId: this.$route.params.questionId
            }
        }).then(res => {
            this.question = res.data.data.question
            this.posts = res.data.data.posts
            console.log(this.question)
            console.log(this.posts)
        },reason => {
            console.log(reason)
        })
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

</style>
