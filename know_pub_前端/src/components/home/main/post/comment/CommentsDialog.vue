<template>
    <div class="dialog-root">
        <div class="body border-0">
            <div class="comment-header d-flex align-items-center w-100">
                <div class="comment-count">{{ total }}条评论</div>
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
            <div
                v-if="total!==0"
                v-infinite-scroll="getComments"
                :infinite-scroll-disabled="isLoading||comments.length===total"
                class="comments d-flex flex-column  w-100 align-items-center"
                infinite-scroll-distance="50"
            >
                <transition-group name="root-comments">
                    <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                                  :comments="rootComment" @refresh="getComments(false)">
                    </root-comment>
                </transition-group>

                <div v-show="isLoading">
                    <el-skeleton :rows="5" :throttle="0.5" animated/>
                </div>
            </div>
            <div v-else class="d-flex justify-content-center align-items-center">
                <el-empty description="还没有评论，快来抢沙发吧~"/>
            </div>
        </div>

        <post-comment :parent="post" @refresh="getComments(false)"></post-comment>
    </div>

</template>

<script>
import RootComment from "@/components/home/main/post/comment/RootComment.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import http, {http_no_token} from "@/utils/http/http";

export default {
    //组件名
    name: "comments-dialog",
    //依赖的组件
    components: {PostComment, RootComment},
    //数据
    data() {
        return {
            pageSize: 10,
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
            rootComments: {},
            post: {}

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
            // 将map转换为数组
            return Array.from(rootComments.values())

        },
        getComments(isMerge = true, orderBy = "likeCount", useParams = false) {
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
                    console.log(resolve)
                    if (resolve.status === 200) {
                        // 连接两个数组
                        if (isMerge) {
                            // 无限滚动的追加
                            this.comments.push(...resolve.data.data.page)
                            this.currentPage += 1
                        } else {
                            // 刷新数据
                            this.comments = resolve.data.data.page
                        }
                        this.total = resolve.data.data.total
                        this.isLoading = false
                        console.log(this.total)
                        console.log(this.comments)
                    } else {
                    }
                }, reason => {
                }
            ).finally(() => {
                this.isLoading = false
            })

        },
        getPost() {
            http_no_token.get('/post/getPost', {
                params: {
                    postId: this.postId
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.post = res.data.data
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
        this.getComments(true)
        this.getPost()


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
            require: true
        },

    }
}
</script>
<style lang="scss">
@include fade(root-comments, 0.9s, (30px, 0, 0));


.dialog-root {
    padding: 20px;
    @include align($fd: column, $ai: start, $jc: start);

    .body {
        width: 100%;
        border: none;

        .comment-header {
            padding: 10px;
            border-bottom: 1px solid #ebebeb;
            font-size: 14px;
            font-weight: bold;

            .comment-count {
            }

            .order {
                margin-left: auto !important;
            }
        }

        .comments {
            @include align($fd: column, $ai: start, $jc: start);
            width: 100%;
            padding: 20px 10px;
            height: 550px;
            overflow-x: hidden;
            overflow-y: scroll;
        }

    }


}


.showMore {
    color: #a2a7a9;

    &:hover {
        cursor: pointer;
        color: #409eff;
    }
}


</style>


