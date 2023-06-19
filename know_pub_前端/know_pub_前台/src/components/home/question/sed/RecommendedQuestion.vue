<template>
    <div class="recommended-question">
        <div class="header">
            <h4>相关问题</h4>
            <div class="el-divider--horizontal"></div>
        </div>
        <div class="body">
            <div v-for="question in questions" :key="question.id" class="questions">
                <span class="title" @click="toQuestion(question.id)">{{ question.content }}</span>
                <span class="count">{{ question.answerCount }}个回答</span>
            </div>
        </div>
    </div>
</template>

<script>
import http, {http_no_token} from "@/utils/http/http";
import {ElMessage} from "element-plus";

export default {
    //组件名
    name: "recommended-question",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            questions: []
        }
    },
    //方法
    methods: {
        toQuestion(id) {
            const routeUrl = this.$router.resolve({
                path: `/home-question/${id}`,
            });
            window.open(routeUrl.href, "_blank");
        },
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        http_no_token.get('/question/recommended-question', {
            params: {
                questionId: this.questionId
            }
        }).then(res => {
            if (res.data.code === 200) {
                this.questions = res.data.data
            } else {
                ElMessage({
                    message: '获取推荐失败',
                    type: 'error'
                })
            }
        }, reason => {
        })
    },
    //侦听器
    watch: {}
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {
        questionId: {
            type: String,
            require: true
        }
    }
}
</script>

<style lang="scss" scoped>
.recommended-question {
  box-shadow: rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px;
  padding: 1em;
  border-bottom: 1px solid $gray;

  .header {
    h4 {
      font-size: 1em;
      font-weight: bold;
    }
  }

  .body {
    display: flex;
    flex-direction: column;
    gap: 1em;

    .questions {
      .title {
        @include clickable();

      }

      .count {
        color: $gray;
        margin-left: 1em;
      }
    }

  }
}
</style>
