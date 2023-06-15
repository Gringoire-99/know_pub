<template>
    <div class="body w-100">
        <comment :comment="rootComment" @refresh="$emit('refresh')"></comment>

        <div v-if="rootComment.childComments.length>0"
             class="child_comments w-100">
            <transition-group name="comments">
                <div v-for="(comment,index) in tempChildComments" :key="index">
                    <comment :comment="comment" :is-show-reply-to="true" @refresh="$emit('refresh')"></comment>
                </div>
            </transition-group>

            <div class="showMore  mb-4">
                <!--            -2>4 有大于4个折叠元素（>4） -2>0有折叠元素(4-1)-->
                <el-button
                    v-show="rootComment.childCount>TEMP_DISPLAY_NUMBER"
                    v-on:click="unfold">
                    {{
                        (rootComment.childCount > NUMBER_ON_HIDE)
                            ? `查看全部${rootComment.childCount}条评论` : `展开其他${rootComment.childCount - TEMP_DISPLAY_NUMBER}条评论`
                    }}
                </el-button>
            </div>
            <div v-if="isLoadingRCD">
                <el-dialog v-model="dialogVisible" :align-center="true" :draggable="true" width="75%">
                    <root-comment-dialog :root-comment="rootComment"></root-comment-dialog>
                </el-dialog>

            </div>
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
            if (this.rootComment.childCount > this.NUMBER_ON_HIDE) {
                this.isLoadingRCD = true
                this.dialogVisible = true
            } else {
                this.TEMP_DISPLAY_NUMBER = this.NUMBER_ON_HIDE
            }
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
        rootComment: {
            deep: true,
            handler(newVal, oldVal) {
                return newVal
            }
        }
    }
    ,
    //计算属性
    computed: {
        tempChildComments() {
            return this.rootComment.childComments.slice(0, this.TEMP_DISPLAY_NUMBER)
        }
    }
    ,
    //绑定父组件的属性
    props: {
        rootComment: {
            type: Object,
            require: true,
        }
    }
}
</script>

<style lang="scss" scoped>
@include fade(comments, 0.1s, (10px, 0, 0));
.body {
    @include align($fd: column);

    .child_comments {
        margin-left: 65px;
        display: flex;
        flex-direction: column;
        justify-content: start !important;
        padding-right: 33px;
    }
}


@media screen and (max-width: 500px) {
    .child_comments {
        margin-left: 20px;
        padding-right: 10px;
    }
}

</style>
