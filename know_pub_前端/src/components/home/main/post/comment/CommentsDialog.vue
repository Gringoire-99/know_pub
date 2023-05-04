<template>
    <el-dialog v-model="dialogVisible" :align-center="true" width="75%">
        <div class="d-flex flex-column">
            <div v-if="total>0" class="comment-dialog root border-0">
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
                <div class="comments dialog-comments d-flex flex-column  w-100 align-items-center"
                     @scroll="loadComments">
                    <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                                  :comments="rootComment"></root-comment>

                    <el-skeleton v-show="isLoading" :rows="5" animated/>
                </div>
            </div>
            <!--    TODO 骨架屏-->
            <div v-else class="d-flex justify-content-center align-items-center">
                还没有评论，快来抢沙发吧~
            </div>
        </div>
        <post-comment></post-comment>
    </el-dialog>
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
            pageIndex: 0,
            isLoading: false,
            isShow: true,
            dialogVisible: false,
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
        sortByLikeCount(array) {

            // 先对根评论排序
            array.sort((a, b) => {
                return b.rootComment.likeCount - a.rootComment.likeCount
            })
            //  再对子评论排序
            array.forEach(item => {
                item.childComments.sort((a, b) => {
                    return b.likeCount - a.likeCount
                })
            })

        },
        sortByPublishTime(array) {
            array.sort((a, b) => {
                return Date.parse(b.rootComment.publishTime) - Date.parse(a.rootComment.publishTime)
            })
            array.forEach(item => {
                item.childComments.sort((a, b) => {
                    return new Date(b.publishTime) - new Date(a.publishTime)
                })
            })
        },
        sortByChildCommentCount(array) {
            array.sort((a, b) => {
                return b.childComments.length - a.childComments.length
            })

        },
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        console.log("created");
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

                    this.comments.push(...resolve.data.data.comments)
                    this.total = resolve.data.data.total
                    this.isLoading = false

                } else {
                    alert("failed")
                }
            }, reason => {
                alert("failed")
            }
        )

    },
    //侦听器
    watch: {
        dialogVisible(newValue, oldValue) {
            if (!newValue) this.$emit('closeDialog')
            return newValue
        },
        visible(newValue, oldValue) {
            console.log(newValue, oldValue)
            this.dialogVisible = newValue
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
            let comments = this.filterRootComments(this.comments)
            let orderBy = this.orderBy
            switch (orderBy) {
                case this.ORDER_BY.PUBLISH_TIME:
                    this.sortByPublishTime(comments)
                    break
                case this.ORDER_BY.LIKE_COUNT:
                    this.sortByLikeCount(comments)
                    break
                case this.ORDER_BY.COMMENT_COUNT:
                    this.sortByChildCommentCount(comments)
                    break
            }
            return comments
        }
    }
    ,
    //绑定父组件的属性
    props: {
        postId: {
            type: String,
            require: true
        },
        visible: {
            type: Boolean,
            default: false
        },

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
<style>
.el-dialog__body {
    padding-top: 0;
}

.el-dialog__header {
    padding-top: 0;
}

.dialog-comments {
    height: 450px;
    overflow-x: hidden;
    overflow-y: scroll;
}
</style>
