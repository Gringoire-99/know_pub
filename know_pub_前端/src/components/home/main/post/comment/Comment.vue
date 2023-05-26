<template>
    <div class="comment w-100 mb-3">
        <div class="avatar me-2">

            <popover
                placement="bottom"
                trigger="hover"
                :show-after="500"
                :width="430"
            >
                <post-card :id="comment.userId"></post-card>
                <template #reference>
                    <el-avatar :src="comment.avatar" shape="square" size="small"></el-avatar>
                </template>
            </popover>

        </div>
        <div class="body d-flex flex-column w-100">
            <div class="header d-flex align-items-center justify-content-start">
                <span class="fw-bold d-flex align-items-center reply">
                     <popover
                         placement="bottom"
                         trigger="hover"
                         :show-after="500"
                         :width="430"
                     >
                         <post-card :id="comment.userId"></post-card>
                        <template #reference>
                            <span class="name">{{ comment.name }}</span>
                        </template>
                    </popover>
                    <span v-if="isShowReplyTo&&comment.replyToUserId" class="d-flex align-items-center">
                        <el-icon><DArrowRight/></el-icon>
                        <popover
                            placement="bottom"
                            trigger="hover"
                            :show-after="500"
                            :width="430"
                        >
                             <post-card :id="comment.replyToUserId"></post-card>
                            <template #reference>
                                <span class="name">{{ comment.replyToUserName }}</span>
                            </template>
                         </popover>

                    </span>
                </span>
                <el-icon class="more">
                    <MoreFilled/>
                </el-icon>
            </div>
            <span class="content">{{ comment.content }}</span>
            <div class="footer d-flex align-items-center">
                <span class="fw-lighter fs-6">{{ comment.createTime.substring(0, 10) }}</span>
                <span v-show="comment.likeCount>200"><span>·</span><span class="label">热评</span></span>
                <div class="operations d-flex align-items-center">

                    <div class="" @click="displayPostComment">
                        <el-icon :size="15">
                            <ChatDotRound/>
                        </el-icon>
                        <span>回复</span>
                    </div>
                    <div :class="{onLike:onLike}" class="ms-2" @click="like">
                        <el-icon :size="17">
                            <Like></Like>
                        </el-icon>
                        <span>{{ comment.likeCount }}</span>
                    </div>
                </div>
            </div>
            <div :class="{'showPostComment':isOpenPostComment}" class="collapse-grid ">
                <div class="post-comment w-100 mt-1 mb-1"
                     @focusout="displayPostComment">
                    <post-comment :is-show-avatar="false" :parent="comment"
                                  :placeholder="`回复 ${comment.name}`" @refresh="$emit('refresh')"></post-comment>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import {ArrowRightBold, ChatDotRound, DArrowRight, MoreFilled} from "@element-plus/icons-vue";
import PostCard from "@/components/user/PostCard.vue";
import Like from "@/components/icons/Like.vue";
import Popover from "@/components/common/Popover.vue";
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";

export default {
    //组件名
    name: "comment",
    //依赖的组件
    components: {Popover, Like, PostCard, DArrowRight, ArrowRightBold, MoreFilled, ChatDotRound, PostComment},
    //数据
    data() {
        return {
            isOpenPostComment: false,
            onLike: false
        }
    },
    //方法
    methods: {
        displayPostComment() {
            this.isOpenPostComment = !this.isOpenPostComment
        },
        like() {
            if (!this.$store.state.isLogin) {
                this.$store.commit("SET_SHOW_LOGIN", true)
                return
            }
            http.post('/comment/like', {
                commentId: this.comment.id
            }).then(res => {
                if (res.data.code === 200) {
                    // 1 点赞 0 取消
                    this.onLike = res.data.data === 1
                    this.comment.likeCount += res.data.data ? 1 : -1
                } else {
                    ElMessage({
                        message: '点赞失败',
                        type: 'error'
                    })
                }
            }, reason => {

            })
        }
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

<style lang="scss" scoped>
.comment {
    @include align($ai: start);

    .avatar {
        .el-avatar {
            cursor: pointer;
            transition-delay: 1s;
        }

    }

    .body {
        .header {
            .reply {

                .el-icon {
                    font-size: 15px;
                    margin-left: 5px;
                    margin-right: 5px;
                    color: #98989a;
                }

                .name {
                    cursor: pointer;
                }
            }

            .more {
                margin-left: auto;
            }

        }

        .content {

        }

        .footer {
            .operations {
                margin-left: auto;
                width: max-content;

                & > * {
                    @include align();
                }

                span {
                    text-wrap: none;
                    text-overflow: ellipsis;
                }

                img {
                    width: 20px;
                    height: 20px;
                    margin-left: 10px;
                }

                div {
                    cursor: pointer;

                    &:hover {
                        color: #2259dc;
                    }
                }

                .onLike {
                    color: $orange;
                }
            }
        }

        .collapse-grid {
            @include gridCollapse(showPostComment, row, 0.2s)

        }
    }
}


@media screen and (max-width: 500px) {
    .footer span {
        display: none;
    }

    .root {
        margin-bottom: 5px !important;
    }
}


</style>
