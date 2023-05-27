<template>
    <div class="root card">
        <div class="card-body">
            <div v-if="isShowAction" class="fw-xsm text-gray d-flex">
                <span>{{ action }}了该回答</span>
                <span class="ms-auto">{{ action.time }}</span>
            </div>

            <!--            问题名-->

            <div v-if="!isAnswerPattern" class="card-title">
                <div class="question"><span class="pointer" @click="toQuestion">{{ post.question }}</span></div>
            </div>

            <transition mode="out-in" name="text">
                <!--            简短介绍-未展开时显示-->
                <div v-if="isCollapseFullText&&!isAnswerPattern" class="intro d-flex w-100">
                    <img v-if="post.cover!==''" :src="post.cover" alt="介绍图片"
                         class="img-fluid col-3 intro-img">

                    <div class="text" v-on:click="collapseFullText">
                        <span ref="contentShort" class="intro-text" v-html="truncatedContent"></span>
                        <div v-show="post.content.length>30">
                            <span class="full-text-btn">...阅读全文</span>
                            <el-icon class="d-inline">
                                <arrow-down/>
                            </el-icon>
                        </div>

                    </div>
                </div>
                <!--            正文-->
                <div v-else class="full-text">
                    <post-body :is-answer-pattern="isAnswerPattern" :post="post"
                               @collapseFullText="isCollapseFullText=true"></post-body>
                </div>
            </transition>


            <!--            操作栏-->
            <div class="card-footer d-flex align-items-center">
                <el-button :class="{on:onLike}" class="agree-btn" type="primary" @click="like">
                    <el-icon>
                        <caret-top></caret-top>
                    </el-icon>
                    <span>赞同</span>
                    {{ post.likeCount }}
                </el-button>
                <el-button :class="{on:onDislike}" class="disagree-btn" type="primary">
                    <el-icon>
                        <caret-bottom></caret-bottom>
                    </el-icon>
                </el-button>
                <div class="etc d-flex align-items-center w-100">
                    <el-button class="comments-btn" v-on:click="collapseComments">
                        <div class="d-flex justify-content-center align-items-center">
                            <el-icon>
                                <ChatDotRound/>
                            </el-icon>
                            <span>
                               {{
                                    isCollapseComments ? post.commentCount > 0 ? `${post.commentCount}条评论` : "添加评论" : '收起评论'
                                }}
                            </span>

                        </div>
                    </el-button>
                    <el-button class="share-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <share></share>
                            </el-icon>
                            <span>分享</span>
                        </div>
                    </el-button>
                    <el-button class="collection-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <star-filled></star-filled>
                            </el-icon>
                            <span>收藏</span>
                        </div>
                    </el-button>
                    <el-popover placement="bottom" popper-class="popover" trigger="click">
                        <template #reference>
                            <el-button class="more-btn">
                                <div class="d-flex justify-content-center align-items-center">
                                    <el-icon class="more">
                                        <MoreFilled/>
                                    </el-icon>
                                </div>

                            </el-button>
                        </template>
                        <ul class="list-group">
                            <li class="list-group-item">不喜欢</li>
                            <li class="list-group-item">举报</li>
                            <li class="list-group-item">屏蔽这个作者</li>
                        </ul>
                    </el-popover>
                    <el-button v-show="!isCollapseComments" class="hideComments" @click="collapseComments">
                        <div class=" d-flex justify-content-center align-items-center">
                            收起
                            <el-icon>
                                <arrow-down></arrow-down>
                            </el-icon>

                        </div>
                    </el-button>
                </div>
            </div>
            <transition name="comment">
                <!--            评论栏-->
                <div v-if="!isCollapseComments" class="card-footer">
                    <post-comment :parent="post" @refresh="$refs.comment.getComments(false)"></post-comment>
                    <comments ref="comment" :post-id="post.id"></comments>
                </div>
            </transition>


        </div>
    </div>

</template>

<script>
import {
    ArrowDown, ArrowUp,
    ArrowUpBold,
    CaretBottom,
    CaretTop,
    Collection,
    Comment, MoreFilled,
    Picture,
    Share,
    StarFilled
} from "@element-plus/icons-vue";
import Mock from "mockjs";

import Comments from "@/components/home/main/post/comment/Comments.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import PostBody from "@/components/home/main/post/PostBody.vue";
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";

/**
 * post：某个主题的一个回答，
 * 包括：
 * 1. 回答者
 * 2. 回答内容
 * 3. 回答评论
 */
export default {
    //组件名
    name: "post",
    //依赖的组件
    components: {
        MoreFilled,
        PostBody,
        ArrowUp,
        PostComment,
        StarFilled,
        Collection, Share, Comment, Comments, CaretBottom, CaretTop, ArrowUpBold, Picture, ArrowDown
    },
    //数据
    data() {
        return {
            isCollapseFullText: true,
            isCollapseComments: true,
            onLike: false,
            onDislike: false,
            action: '',
            content: ''
        }
    }, //绑定父组件的属性
    props: {
        post: {
            type: Object,
            require: true
        },
        isShowAction: {
            type: Boolean,
            default: false
        },
        isAnswerPattern: {
            type: Boolean,
            default: false
        }


    },
    //方法
    methods: {
        collapseFullText() {
            this.isCollapseFullText = !this.isCollapseFullText;
        },
        collapseComments() {
            this.isCollapseComments = !this.isCollapseComments;
        },
        toQuestion() {
            const routeUrl = this.$router.resolve({
                path: `/home-question/${this.post.questionId}`,
            });
            window.open(routeUrl.href, "_blank");
        },
        like() {
            if (!this.$store.state.isLogin) {
                this.$store.commit("SET_SHOW_LOGIN", true)
                return
            }
            console.log(this.post.id)
            http.post('/post/like', {
                postId: this.post.id
            }).then(res => {
                if (res.data.code === 200) {
                    this.post.likeCount += this.onLike ? -1 : 1
                    this.onLike = !this.onLike
                    ElMessage({
                        message: this.onLike ? '点赞成功' : '取消点赞成功',
                        type: this.onLike ? 'success' : 'info'
                    })
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
        if (this.post.liked) {
            this.onLike = true
            this.action += '点赞'
        }

    },
    //侦听器
    watch: {},
    //计算属性
    computed: {
        truncatedContent() {
            const wordLimit = 30;
            const contentWords = this.post.content.split(' ');

            if (contentWords.length <= wordLimit) {
                return this.post.content;
            } else {
                const truncatedWords = contentWords.slice(0, wordLimit);
                return truncatedWords.join(' ') + '...';
            }
        }
    }
    ,

}
</script>

<style lang="scss" scoped>
@include fade(text, 0.2s, (-10px, 0, 0));
@include fade(comment, 0.2s, (0, -10px, 0));
.list-group {
    padding: 0;
    margin: 0;
    border: none;

    .el-popover.el-popper {
        padding: 0
    }

    .list-group-item {
        border: none;

        &:hover {
            background: #f5f5f5;
        }
    }

}


.card-footer {

    margin-top: 15px;
    padding: 0;
    border: none;
    background-color: white;

    & > .el-button {
        @include clickEffect($white, $scale: 0.95, $bg: $deep-blue);
        color: $deep-blue;
        border: none;
        background: $whiteBlue;
        font-size: $fs-large;

        &.disagree-btn {
            width: 35px;
            margin-left: 5px;
        }

        &.agree-btn {
            padding-left: 10px;

        }

        &.on {
            background: $deep-blue;
            color: $white;
        }
    }

    .etc {
        .el-button {
            border: none;
            background: white;
            font-size: 16px;
            margin-left: 5px;
        }

        .el-button {
            .el-icon {
                font-size: 20px;
                margin-bottom: 2px;
                margin-right: 3px;
            }
        }
    }
}


.hideComments {
    margin-left: auto !important;
}


.full-text-btn {
    color: #0470bd;
    cursor: pointer;
}


.question {
    /*换行*/
    word-wrap: anywhere;
    font-size: 1.3rem;
    margin-bottom: 10px;
    font-weight: bold;
}

.intro-img {
    transition: all 0.5s;
}

.intro {
    & > img {
        border-radius: 5px;
        margin-right: 20px;
        min-width: 200px;
        min-height: 100px;
        max-height: 130px;
        object-fit: cover;
    }

    .text {
        .el-icon {
            font-size: 20px;
            color: #0470bd;
            margin-left: 5px;
            @include clickEffect()
        }
    }
}


.intro .text:hover {
    color: rgba(91, 88, 88, 0.86);
    cursor: pointer;
}

@media screen and (max-width: 500px) {
    .intro-img {
        flex: 1 1 auto;
        margin: 0 !important;
    }

    .intro {
        flex-wrap: wrap;
    }

    .card-footer span {
        display: none;
    }

    .card-footer .el-button {
        margin-left: 5px;
        margin-right: 5px;
        padding-right: 5px;
        padding-left: 5px;
    }

    .root, .card-body, .card-footer {
        padding: 0 !important;
    }

}

.root {
    overflow: hidden;
    border: none;
    border-bottom: 1px solid #ebebeb;
}


</style>
