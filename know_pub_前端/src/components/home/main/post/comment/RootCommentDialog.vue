<template>
    <div class="dialog-root">
        <div class="header fw-bold mb-3 mt-3">评论回复</div>
        <div class="body border-0">

            <comment :comment="rootComment"></comment>

            <div class="divider w-100"></div>
            <div class="count">{{ `${total}条回复` }}</div>
            <div v-infinite-scroll="getComments" :infinite-scroll-disabled="isLoading"
                 class="comments pe-2"
                 infinite-scroll-distance="10">
                <transition-group
                    name="comments"
                >
                    <comment v-for="childComment in comments" :key="childComment.id" :comment="childComment"
                    ></comment>
                </transition-group>

                <el-skeleton :rows="3" :throttle="0.5" animated/>
            </div>
            <div v-if="total===0&&!isLoading" class="d-flex justify-content-center align-items-center">
                <el-empty description="还没有评论，快来抢沙发吧~"/>
            </div>
        </div>

        <post-comment></post-comment>
    </div>

</template>

<script>

import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import http from "@/utils/http/http";
import Comment from "@/components/home/main/post/comment/Comment.vue";

export default {
    //组件名
    name: "root-comment-dialog",
    //依赖的组件
    components: {Comment, PostComment},
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
        getComments(isMerge = true, orderBy = "likeCount", useParams = false) {
            if (this.isLoading) return
            this.isLoading = true
            let order = useParams ? orderBy : this.orderBy
            http.get('/comments/child-comments', {
                params: {
                    id: this.rootComment.id,
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
        this.getComments(false, this.orderBy, true)

    },
    //侦听器
    watch: {}
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {

        rootComment: {
            type: Object,
            require: true
        }
    }
}
</script>

<style lang="scss" scoped>
@include fade(comments, 0.5s, 10px);
.dialog-root {
    padding: 20px;

    .header {
    }


    .body {
        @include align($fd: column, $jc: start, $ai: start);
        margin-top: 10px;
        padding: 10px 0px;
        width: 100%;

        .count {
            text-align: start;
        }

        .divider {
            height: 20px;
            background: rgba(217, 219, 225, 0.36);
        }

        .comments {
            height: 450px;
            overflow-x: hidden;
            overflow-y: scroll;
        }
    }

}


</style>
<style>
.root-comment-dialog-comments {

}
</style>
