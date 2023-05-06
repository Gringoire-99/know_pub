<template>
    <div class="d-flex justify-content-start align-items-start w-100 mb-3">
        <div class="avatar me-2 pointer">
            <el-popover
                placement="bottom"
                trigger="hover"
                width="430"
            >
                <post-card :id="comment.publisherId"></post-card>
                <template #reference>
                    <el-avatar :src="comment.avatar" shape="square" size="small"></el-avatar>
                </template>
            </el-popover>
        </div>
        <div class="d-flex flex-column w-100">
            <div class="d-flex align-items-center justify-content-start">
                <span class="fw-bold d-flex align-items-center reply">
                     <el-popover
                         placement="bottom"
                         trigger="hover"
                         width="430"
                     >
                         <post-card :id="comment.publisherId"></post-card>
                        <template #reference>
                            <span class="pointer">{{ comment.name }}</span>
                        </template>
                    </el-popover>
                    <span v-if="isShowReplyTo" class="d-flex align-items-center">
                        <el-icon><DArrowRight/></el-icon>
                        <el-popover
                            placement="bottom"
                            trigger="hover"
                            width="430"
                        >
                         <post-card :id="comment.replyToId"></post-card>
                        <template #reference>
                            <span class="pointer">{{ comment.replyToName }}</span>
                        </template>
                    </el-popover>

                    </span>
                </span>

                <el-icon class="more">
                    <MoreFilled/>
                </el-icon>
            </div>
            <span>{{ comment.content }}</span>
            <div class="footer d-flex align-items-center">
                <span class="fw-lighter fs-6">{{ comment.publishTime.substring(0, 10) }}</span>
                <span v-show="comment.likeCount>200"><span>·</span><span class="label">热评</span></span>
                <div class="operations d-flex align-items-center">

                    <div class="d-flex align-items-center" @click="displayPostComment">
                        <el-icon :size="15">
                            <ChatDotRound/>
                        </el-icon>
                        <span>回复</span>
                    </div>
                    <div class="d-flex align-items-center ms-2">
                        <el-icon :size="17">
                            <svg class="icon" height="200" p-id="12871" t="1683120992681"
                                 version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M292 417.7c-17.6 0.9-43.4 1.3-82.6 1.3-26.8 0-42.1 18.2-42.1 42.7 0 24.5-0.1 363-0.1 388s8.2 38.2 37.9 38.2l86.9 0.1V417.7zM519.7 188.7c9.4 11.2 22.7 44.1 21.6 79.1-0.6 19.6-4.7 33.3-16.2 62.1l-4 10.1c-12.8 31.8-24.9 61.9-2.9 94.5 13.4 19.8 35.3 31.1 60 31.1h220.4c10.6 0 14.4 6.8 15.7 12.5L735 819.6c-1.6 0.3-3.3 0.6-4.7 0.8l-327.3-0.3V447.5c56.4-47.1 80.2-124.5 94.7-187.2 3.2-14 5.4-27.2 7.3-38.8 1.8-10.6 4.1-24.6 6.4-31.5 1.7-0.5 4.5-1 8.3-1.3m6.7-68.3H525c-88.6 2.7-78.3 58.8-93.5 124.6-13.3 57.9-34.1 120.7-74.9 152.6-11.3 4.5-16.6 8.2-21.5 11.1V888l397.1 0.2c3.4 0 25-1.9 38.7-10.1 23.7-14.5 27.7-32.5 27.7-32.5L882.8 483c-0.2-29.7-21-85.3-84.2-85.3H578.3c-8.1 0-5-5.5 9.9-42.7 12.7-31.9 20.1-54 21-85.3 1.9-67.4-32.3-149.3-82.8-149.3z"
                                    fill="" p-id="12872"></path>
                            </svg>
                        </el-icon>
                        <span>{{ comment.likeCount }}</span>
                    </div>
                </div>
            </div>
            <div v-show="isOpenPostComment" class="w-100 mt-1 mb-1" @focusout="displayPostComment">
                <post-comment :is-show-avatar="false" :placeholder="`回复 ${comment.name}`"></post-comment>
            </div>
        </div>
    </div>
</template>

<script>
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import {ArrowRightBold, ChatDotRound, DArrowRight, MoreFilled} from "@element-plus/icons-vue";
import PostCard from "@/components/user/PostCard.vue";

export default {
    //组件名
    name: "comment",
    //依赖的组件
    components: {PostCard, DArrowRight, ArrowRightBold, MoreFilled, ChatDotRound, PostComment},
    //数据
    data() {
        return {
            isOpenPostComment: false
        }
    },
    //方法
    methods: {
        displayPostComment() {
            this.isOpenPostComment = !this.isOpenPostComment
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
        comment: {
            type: Object,
            require: true
        },
        isShowReplyTo: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style scoped>
.reply .el-icon {
    font-size: 15px;
    margin-left: 5px;
    margin-right: 5px;
    color: #98989a;
}

.more {
    margin-left: auto;
}

.label {
    color: #e7800a;
}

.operations {
    margin-left: auto;
}

.operations img {
    width: 20px;
    height: 20px;
    margin-left: 10px;
}

.operations div {
    cursor: pointer;
}

.operations div:hover {
    color: #2259dc;
}


</style>
