<template>
    <div class="dialog-root">
        <div class="d-flex flex-column">
            <div class="body border-0">
                <div class="comment-header d-flex align-items-center w-100">
                    <div class="comment_count">{{ total }}条评论</div>
                    <el-radio-group v-model="orderBy" class="order" size="small">
                        <el-radio-button :label="ORDER_BY.LIKE_COUNT" @click="changeOrderBy(ORDER_BY.LIKE_COUNT)">默认
                        </el-radio-button>
                        <el-radio-button :label="ORDER_BY.PUBLISH_TIME" @click="changeOrderBy(ORDER_BY.PUBLISH_TIME)">
                            最新
                        </el-radio-button>
                        <el-radio-button :label="ORDER_BY.COMMENT_COUNT" @click="changeOrderBy(ORDER_BY.COMMENT_COUNT)">
                            最热
                        </el-radio-button>
                    </el-radio-group>
                </div>
                <div v-infinite-scroll="getComments"
                     :infinite-scroll-disabled="isLoading" class="comments comment-dialog-comments d-flex flex-column  w-100 align-items-center"
                     infinite-scroll-distance="30"
                >
                    <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                                  :comments="rootComment"></root-comment>
                    <el-skeleton :rows="5" :throttle="0.5" animated/>
                </div>

            </div>
            <div v-if="total===0&&!isLoading" class="d-flex justify-content-center align-items-center">
                <el-empty description="还没有评论，快来抢沙发吧~"/>
            </div>
        </div>
        <post-comment></post-comment>
    </div>

</template>

<script>
import RootComment from "@/components/home/main/post/comment/RootComment.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import http from "@/utils/http/http";

export default {
    //组件名
    name: "comments-dialog",
    //依赖的组件
    components: {PostComment, RootComment},
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

        }
    },
    //方法
    methods: {
        changeOrderBy(orderBy) {
            this.orderBy = orderBy
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
        getComments(isMerge = true, orderBy = "likeCount", useParams = false) {
            if (this.isLoading) return
            this.isLoading = true
            let order = useParams ? orderBy : this.orderBy
            http.get('/comments', {
                params: {
                    postId: this.postId,
                    pageSize: this.pageSize,
                    currentPage: this.currentPage,
                    orderBy: order
                }
            }).then(
                resolve => {
                    if (resolve.status === 200) {
                        // 连接两个数组
                        if (isMerge) {
                            this.comments.push(...resolve.data.data.comments)
                            this.currentPage += this.pageSize
                        } else {
                            this.comments = resolve.data.data.comments
                            this.currentPage = this.pageSize
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
        orderBy(newValue, oldValue) {
            this.getComments(false, newValue, true)
            return newValue
        },
        comments: {
            handler(newValue, oldValue) {
                this.filterRootComments(newValue)
                return newValue
            },
            deep: true
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
            require: true
        },

    }
}
</script>
<style lang="scss">
.comment-dialog-comments {
    height: 600px;
    overflow-x: hidden;
    overflow-y: scroll;
}
</style>
<style scoped>
.dialog-root {
    padding: 20px;

    .body {

    }
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


