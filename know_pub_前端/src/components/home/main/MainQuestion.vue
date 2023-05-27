<template>
    <div class="main-question">
        <div class="header">
            <!--            TODO-->
        </div>
        <div class="body">
            <transition-group name="questions">
                <div v-for="question in questions" :key="question.id" class="question">
                    <question :question="question"></question>
                </div>
            </transition-group>
            <el-skeleton v-show="isLoading" :rows="15"></el-skeleton>
        </div>
    </div>
</template>

<script>
import Question from "@/components/home/question/Question.vue";
import {http_no_token} from "@/utils/http/http";

export default {
    //组件名
    name: "main-question",
    //依赖的组件
    components: {Question},
    //数据
    data() {
        return {
            currentPage: 1,
            pageSize: 10,
            total: 0,
            questions: [],
            isLoading: false
        }
    },
    //方法
    methods: {
        getQuestions(isMerge = false) {
            if (this.isLoading) return
            this.isLoading = true
            if (!isMerge) {
                this.currentPage = 1
            }
            http_no_token.get('/question/questions', {
                params: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                if (res.data.code === 200) {
                    if (isMerge) {
                        this.questions.push(...res.data.data.page)
                    } else {
                        this.questions = res.data.data.page
                    }
                    this.currentPage++
                    console.log(this.questions)
                }
            }, reason => {
            }).finally(() => {
                this.isLoading = false
                this.$store.commit('SET_LOAD_DATA', false)
            })
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getQuestions()
    },
    //侦听器
    watch: {
        $store: {
            handler() {
                if (this.$store.state.loadData) {
                    this.getQuestions(true)
                }
            },
            deep: true
        }
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
@include fade(questions, 0.4s, (-30px, 0, 0));
.body {
    display: flex;
    flex-direction: column;
    gap: 1em;

}

</style>
