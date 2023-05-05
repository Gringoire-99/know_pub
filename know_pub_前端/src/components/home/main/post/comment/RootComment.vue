<template>

    <comment :comment="comments.rootComment"></comment>

    <div v-if="comments.childComments.length>0"
         class="child_comments w-100">
        <div v-for="(comment,index) in tempChildComments" :key="index">
            <comment :comment="comment"></comment>
        </div>

        <div class="showMore mt-4 mb-4">
            <!--            -2>4 有大于4个折叠元素（>4） -2>0有折叠元素(4-1)-->
            <el-button
                v-show="comments.childComments.length>TEMP_DISPLAY_NUMBER&&tempChildComments.length<=TEMP_DISPLAY_NUMBER"
                v-on:click="unfold">
                {{
                    (comments.childComments.length > NUMBER_ON_HIDE)
                        ? `查看全部${comments.childComments.length}条评论` : `展开其他${comments.childComments.length - TEMP_DISPLAY_NUMBER}条评论`
                }}
            </el-button>
        </div>
        <div v-if="isLoadingRCD">
            <root-comment-dialog :root-comment="comments.rootComment" :visible="dialogVisible"
                                 @closeDialog="dialogVisible=false"></root-comment-dialog>
        </div>
    </div>

</template>

<script>
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import Comment from "@/components/home/main/post/comment/Comment.vue";
import RootCommentDialog from "@/components/home/main/post/comment/RootCommentDialog.vue";

export default {
    //组件名
    name: "root-comment",
    //依赖的组件
    components: {RootCommentDialog, PostComment, Comment},
    //数据
    data() {
        return {
            // 根评论的最大显示数量，超过的部分通过点击展开
            TEMP_DISPLAY_NUMBER: 2,
            // 根评论的最大隐藏数量，超过的部分通过popover展示
            NUMBER_ON_HIDE: 4,
            tempChildComments: [],
            isOpenPostComment: false,
            isLoadingRCD: false,
            dialogVisible: false
        }
    },
    //方法
    methods: {
        displayPostComment() {
            this.isOpenPostComment = !this.isOpenPostComment
        },
        unfold() {
            if (this.comments.childComments.length > this.NUMBER_ON_HIDE) {
                this.isLoadingRCD = true
                this.dialogVisible = true
            } else {
                this.tempChildComments.push(...this.comments.childComments.slice(this.TEMP_DISPLAY_NUMBER))
            }
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        // 每个RootComment最只展示两个子评论，其余的子评论通过点击展开，子评论过多时通过
        this.tempChildComments = this.comments.childComments.slice(0, this.TEMP_DISPLAY_NUMBER)
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
        comments: {
            rootComment: {},
            childComments: [],
            type: Object,
            require: true
        }
    }
}
</script>

<style scoped>


.child_comments {
    margin-left: 65px;
    display: flex;
    flex-direction: column;
    justify-content: start !important;
    padding-right: 33px;
}

</style>
