<template>
    <div class="root-question">
        <div class="header">
            <div v-if="question.isAnonymous===0" class="userInfo">
                <popover
                        :show-after="500"
                        :width="430"
                        placement="bottom"
                        trigger="hover"
                >
                    <post-card :id="question.userId"></post-card>
                    <template #reference>
                        <div class="d-flex align-items-center gap-1">
                            <el-avatar :src="question.avatar"></el-avatar>
                            <span class="name">{{ question.name }} </span>
                        </div>
                    </template>
                </popover>
            </div>
            <div v-else class="userInfo">
                <el-avatar
                        src="https://pic1.zhimg.com/v2-d41c2ceaed8f51999522f903672a521f_l.jpg?source=117bd250"></el-avatar>
                <span class="name">匿名用户 </span>
            </div>
            <span> 的提问</span>
            <span class="fw-lighter text-gray">{{ String(question.createTime).substring(0, 10) }} 期待你的回答</span>
        </div>
        <div class="body">
            <span class="title" @click="toQuestion">{{ question.question }}</span>
            <el-button type="primary" @click="toQuestion">
                <el-icon>
                    <edit-pen></edit-pen>
                </el-icon>
                <span>写回答</span>
            </el-button>
        </div>
        <div class="footer">
            <span>最近{{ question.viewCount }}人看过</span><span>·</span>
            <span>{{ question.answerCount }}人回答</span><span>·</span>
            <span>{{ question.collectionCount }}关注</span>
        </div>
    </div>
</template>

<script>
import Popover from "@/components/common/Popover.vue";
import PostCard from "@/components/user/PostCard.vue";
import Write from "@/components/icons/Write.vue";
import {EditPen} from "@element-plus/icons-vue";

export default {
    //组件名
    name: "question",
    //依赖的组件
    components: {EditPen, Write, PostCard, Popover},
    //数据
    data() {
        return {}
    },
    //方法
    methods: {
        toQuestion() {
            const routeUrl = this.$router.resolve({
                path: `/home-question/${this.question.id}`,
            });
            window.open(routeUrl.href, "_blank");
        },
    },
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
        question: {
            type: Object,
            require: true
        }
    }
}
</script>

<style lang="scss" scoped>
.root-question {
  display: flex;
  flex-direction: column;
  gap: 0.5em;
  @extend %card-border-bottom;

  & > * {
    display: flex;
  }

  .header {
    align-items: center;
    gap: 0.5em;

    .userInfo {
      cursor: pointer;
    }

    .el-avatar {
      max-width: 2em;
      max-height: 2em;
      object-fit: cover;
      @include align()
    }

    & > * {
      @include align();
      gap: 0.5em;
    }
  }

  .body {
    display: flex;
    align-items: center;

    .title {
      @include clickable($black)
    }

    .el-button {
      margin-left: auto;
      background: $deep-blue;
    }
  }

  .footer {
    font-weight: lighter;
    color: $gray;
  }
}
</style>
