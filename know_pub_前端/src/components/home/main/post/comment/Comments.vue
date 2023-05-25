<template>
    <div class="root">
        <div class="comment-header d-flex align-items-center w-100">
            <div class="comment-count">{{ total }}条评论</div>
            <el-radio-group v-model="orderBy" class="order" size="small">
                <el-radio-button :label="ORDER_BY.LIKE_COUNT" @click="changeOrderBy(ORDER_BY.LIKE_COUNT)">默认
                </el-radio-button>
                <el-radio-button :label="ORDER_BY.PUBLISH_TIME" @click="changeOrderBy(ORDER_BY.PUBLISH_TIME)">最新
                </el-radio-button>
                <el-radio-button :label="ORDER_BY.COMMENT_COUNT" @click="changeOrderBy(ORDER_BY.COMMENT_COUNT)">最热
                </el-radio-button>
            </el-radio-group>
        </div>
        <div class="comments">
            <transition-group
                name="comments"
            >
                <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                              :comments="rootComment" @refresh="getComments(false)">
                </root-comment>

            </transition-group>

            <div v-if="isLoading&&total===0" class="d-flex align-items-center showMore" v-on:click="openCommentsDialog">
                <span>{{ `点击查看所有${total}条评论` }}</span>
                <el-icon>
                    <arrow-right></arrow-right>
                </el-icon>
            </div>
        </div>
        <div v-if="!isLoading&&total===0" class="d-flex justify-content-center align-items-center">
            <el-empty description="还没有评论，快来抢沙发吧~"/>
        </div>
        <el-skeleton v-if="isLoading" :rows="5" :throttle="0.5" animated/>

        <div v-if="isLoadDialog">
            <el-dialog v-model="dialogVisible" :align-center="true" :draggable="true" width="85%">
                <comments-dialog :post-id="postId"></comments-dialog>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import RootComment from "@/components/home/main/post/comment/RootComment.vue";
import {ArrowRight} from "@element-plus/icons-vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import CommentsDialog from "@/components/home/main/post/comment/CommentsDialog.vue";


export default {
    //组件名
    name: "comments",
    //依赖的组件
    components: {CommentsDialog, PostComment, ArrowRight, RootComment},
    //数据
    data() {
        return {
            pageSize: 5,
            currentPage: 1,
            isLoading: false,

            // 默认按照点赞数排序
            ORDER_BY: {
                PUBLISH_TIME: "publishTime",
                LIKE_COUNT: "likeCount",
                // 最热：根评论的点赞数+子评论最多
                COMMENT_COUNT: "commentCount",
            },
            orderBy: "likeCount",
            comments: [],
            total: 0,
            dialogVisible: false,
            isLoadDialog: false,
            rootComments: []

        }
    },
    //方法
    methods: {
        changeOrderBy(orderBy) {
            this.orderBy = orderBy
        },
        openCommentsDialog() {
            this.dialogVisible = true
            this.isLoadDialog = true
        },
        filterRootComments(comments) {
            // 筛选出根评论
            let rootComments = new Map()
            comments.forEach(comment => {
                if (comment.isRootComment === 1) {
                    if (!rootComments.has(comment.id)) {
                        rootComments.set(comment.id, {
                            rootComment: comment,
                            childComments: []
                        })
                    }
                } else {
                    if (rootComments.has(comment.rootCommentId)) {
                        rootComments.get(comment.rootCommentId).childComments.push(comment)
                    } else {
                        rootComments.set(comment.rootCommentId, {
                            rootComment: null,
                            childComments: [comment]
                        })
                    }
                }

            })
            console.log(rootComments.values())
            // 将map转换为数组
            return Array.from(rootComments.values())

        },
        getComments(isMerge = true) {
            console.log('load')

            if (this.isLoading) return
            if (!isMerge) {
                this.currentPage = 1
            }
            this.isLoading = true
            http.get('/comment/comments', {
                params: {
                    postId: this.postId,
                    pageSize: this.pageSize,
                    currentPage: this.currentPage,
                }
            }).then(
                resolve => {
                    if (resolve.status === 200) {
                        // 连接两个数组
                        if (isMerge) {
                            // 无限滚动的追加
                            this.comments.push(...resolve.data.data.page)
                            this.currentPage += 1
                        } else {
                            // 刷新数据
                            this.comments = resolve.data.data.page
                            this.currentPage = this.pageSize
                        }
                        this.total = resolve.data.data.total
                        this.isLoading = false
                    } else {
                    }
                }, reason => {
                }
            )
        }


    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getComments(false)
    },


    //侦听器
    watch: {
        comments: {
            deep: true,
            handler(newVal, oldVal) {
                this.rootComments = this.filterRootComments(newVal)
                return newVal
            }
        },
        orderBy(newValue, oldValue) {
            this.getComments(false)
            return newValue
        },
        rootComments: {
            deep: true,
            handler(newVal, oldVal) {
                return newVal
            }
        }

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

    }
}
</script>

<style lang="scss" scoped>
@include fade(comments, 0.5s, (10px, 0, 0), $reverse: false);
@media screen and (max-width: 768px) {
    .root {
        padding: 0 !important;
    }
}

.el-radio-group {
    flex-wrap: nowrap;
}

.root {
    margin-top: 10px;
    border: 1px solid #ebebeb;
    border-radius: 3px;
    padding: 10px 20px;
    transition: height 2s;

}

.comment-header {
    padding: 10px;
    border-bottom: 1px solid #ebebeb;
    font-size: 14px;
    font-weight: bold;
}

.showMore {
    color: #a2a7a9;

    &:hover {
        cursor: pointer;
        color: #409eff;
    }
}


.comments {
    @include align($fd: column);
    padding: 20px 10px;


}

.order {
    margin-left: auto !important;
}

</style>
<style>

</style>
