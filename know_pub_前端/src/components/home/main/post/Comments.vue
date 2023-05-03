<template>
    <div v-if="comments.length>0" class="root">
        <div class="comment-header d-flex align-items-center w-100">
            <div class="comment_count">{{ comments.length }}条评论</div>
            <el-radio-group v-model="orderBy.likeCount" class="order" size="small">
                <el-radio-button :label="orderBy.likeCount">默认</el-radio-button>
                <el-radio-button :label="orderBy.publishTime">最新</el-radio-button>
                <el-radio-button :label="orderBy.commentCount">最热</el-radio-button>
            </el-radio-group>
        </div>
        <div class="comments d-flex flex-column  w-100 align-items-center justify-content-center">
            <root-comment v-for="rootComment in rootComments" :key="rootComment.rootComment.id"
                          :comments="rootComment"></root-comment>
        </div>
    </div>
    <div v-else>
        还没有评论，快来抢沙发吧
    </div>
</template>

<script>
import http from "@/utils/http/http";
import RootComment from "@/components/home/main/post/RootComment.vue";

/**
 * 评论comments默认按照点赞数排序
 * 首先筛选出直接回复根post的评论（根评论），其他的评论都是根评论的子评论
 * 遍历comments,先创建根评论数组，判断comment的根评论是否在这个数组里，不在则根据根评论的id创建根评论对象{子评论,根评论}
 * 在则将comment添加到根评论的子评论数组中
 *
 *
 *
 */

export default {
    //组件名
    name: "comments",
    //依赖的组件
    components: {RootComment},
    //数据
    data() {
        return {
            pageSize: 10,
            pageIndex: 0,
            isLoading: false,

            // 默认按照点赞数排序
            orderBy: {
                publishTime: "publish_time",
                likeCount: "like_count",
                // 最热：根评论的点赞数+子评论最多
                commentCount: "comment_count",
            },
            comments: [],
            rootComments: []

        }
    },
    //方法
    methods: {
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
            this.rootComments = Array.from(rootComments.values())

        },
        sortByLikeCount(array) {
            array.sort((a, b) => {
                return b.rootComment.likeCount - a.likeCount
            })
        },
        sortByPublishTime(array) {
            array.sort((a, b) => {
                return b.rootComment.publishTime - a.publishTime
            })
        },
        sortByChildCommentCount(array) {
            array.sort((a, b) => {
                return a.childComments.length - b.childComments.length
            })
        },


    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        console.log("created")
        http.get('/comments', {
            params: {
                postId: this.postId,
                pageSize: this.pageSize,
                pageIndex: this.pageIndex,
                orderBy: this.orderBy.likeCount
            }
        }).then(
            resolve => {
                if (resolve.status === 200) {

                    // 连接两个数组
                    this.comments.push(...resolve.data)
                    this.isLoading = false

                    this.filterRootComments(this.comments)
                } else {
                    alert("failed")
                }
            }, reason => {
                alert("failed")
            }
        )
    },


    //侦听器
    watch: {}
    ,
    //计算属性
    computed: {
        // rootComments:{
        //     get(){
        //         return this.rootComments
        //     },
        //     set(value){
        //         this.rootComments = value
        //     }
        // }
    }
    ,
    //绑定父组件的属性
    props: {
        postId: {
            type: String,
            required: true
        },
        // 是否是完整的评论列表，否：只加载10条，没有无限加载方法，是：划到底自动加载
        isComplete: {
            type: Boolean,
            default: false,
            required: true
        }
    }
}
</script>

<style scoped>
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

.comments {
    padding: 20px 10px;
}

.order {
    margin-left: auto !important;
}
</style>
