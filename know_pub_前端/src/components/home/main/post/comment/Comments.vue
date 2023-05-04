<template>
  <!--    TODO 待抽取-->
    <div v-if="total>0" class="root">
        <div class="comment-header d-flex align-items-center w-100">
            <div class="comment_count">{{ total }}条评论</div>
            <el-radio-group v-model="orderBy" class="order" size="small">
                <el-radio-button :label="ORDER_BY.LIKE_COUNT" @click="changeOrderBy(ORDER_BY.LIKE_COUNT)">默认
                </el-radio-button>
                <el-radio-button :label="ORDER_BY.PUBLISH_TIME" @click="changeOrderBy(ORDER_BY.PUBLISH_TIME)">最新
                </el-radio-button>
                <el-radio-button :label="ORDER_BY.COMMENT_COUNT" @click="changeOrderBy(ORDER_BY.COMMENT_COUNT)">最热
                </el-radio-button>
            </el-radio-group>
        </div>
        <div class="comments d-flex flex-column  w-100 align-items-center justify-content-center">
            <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                          :comments="rootComment"></root-comment>
            <div class="d-flex align-items-center showMore" v-on:click="openCommentsDialog">
                <span>{{ `点击查看所有${total}条评论` }}</span>
                <el-icon>
                    <arrow-right></arrow-right>
                </el-icon>
            </div>
        </div>
    </div>
  <!--    TODO 骨架屏-->
    <div v-if="!isLoading&&total===0" class="d-flex justify-content-center align-items-center">
        还没有评论，快来抢沙发吧~
    </div>
    <el-skeleton v-if="isLoading" :rows="5" animated/>
    <comments-dialog v-model:visible="dialogVisible" :post-id="postId"
                     @closeDialog="dialogVisible=false"></comments-dialog>
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
            pageIndex: 0,
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
            dialogVisible: false,
            total: 0


        }
    },
    //方法
    methods: {
        changeOrderBy(orderBy) {
            this.orderBy = orderBy
        },
        openCommentsDialog() {
            this.dialogVisible = true
        },
        filterRootComments(comments) {
            // 筛选出根评论
            let rootComments = new Map()
            comments.forEach(comment => {
                if (comment.rootCommentId === comment.postId) {
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
            // 将map转换为数组
            return Array.from(rootComments.values())

        },
        getComments(isMerge = true) {
            if (this.isLoading) return
            console.log("get Comments");
            this.isLoading = true
            http.get('/comments', {
                params: {
                    postId: this.postId,
                    pageSize: this.pageSize,
                    pageIndex: this.pageIndex,
                    orderBy: this.orderBy
                }
            }).then(
                resolve => {
                    if (resolve.status === 200) {
                        // 连接两个数组
                        console.log(resolve.data.data.comments.length);
                        if (isMerge) {
                            this.comments.push(...resolve.data.data.comments)
                            this.pageIndex += this.pageSize
                        } else {
                            this.comments = resolve.data.data.comments
                            this.pageIndex = this.pageSize
                        }
                        this.total = resolve.data.data.total
                        this.isLoading = false
                    } else {
                        alert("failed")
                    }
                }, reason => {
                    alert("failed")
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
            handler(newValue, oldValue) {
                this.filterRootComments(newValue)
                return true
            },
            deep: true
        },
        orderBy(newValue, oldValue) {
            this.getComments(false)
            return newValue
        },

    }
    ,
    //计算属性
    computed: {
        rootComments() {
            return this.filterRootComments(this.comments)
        }
    }
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

<style scoped>
.comments-dialog {
    overflow: scroll;
    overflow-y: hidden;
}

.root {
    margin-top: 10px;
    border: 1px solid #ebebeb;
    border-radius: 3px;
    padding: 10px 20px;
}

.comment-header {
    padding: 10px;
    border-bottom: 1px solid #ebebeb;
    font-size: 14px;
    font-weight: bold;
}

.showMore {
    color: #a2a7a9;
}

.showMore:hover {
    cursor: pointer;
    color: #409eff;
}

.comments {
    padding: 20px 10px;
}

.order {
    margin-left: auto !important;
}

</style>
