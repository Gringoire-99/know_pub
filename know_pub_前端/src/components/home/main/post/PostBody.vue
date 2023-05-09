<template>
    <div class="d-flex align-items-start flex-column">

        <div class="authorInfo ps-0 pe-0 d-flex align-items-center w-100">
            <el-popover
                placement="bottom"
                trigger="hover"
                width="430"
            >
                <div v-if="isLoadingPostCard" class="post-card">
                    <post-card :id="post.author.id"></post-card>
                </div>
                <template #reference>
                    <div class="d-flex align-items-center pointer"
                         v-on:mouseover="isLoadingPostCard=true">
                        <el-image :src="post.author.avatar">
                            <template #error>
                                <div class="image-slot">
                                    <el-image>
                                        <Picture></Picture>
                                    </el-image>
                                </div>
                            </template>
                        </el-image>
                        <span class="ms-2 me-2">{{ post.author.name }},</span>
                        <span class="fw-lighter fst-normal fs-6"
                        >{{
                                post.author.desc.length > 8 ? post.author.desc.substring(0, 8) + "..." : post.author.desc
                            }}</span>
                    </div>

                </template>
            </el-popover>


            <div class="hideFullText ms-auto d-flex align-items-center" @click="collapseFullText">收起回复
                <el-icon>
                    <arrow-up></arrow-up>
                </el-icon>
            </div>
        </div>
        <div class="mt-3 mb-3 fw-lighter">{{ `${post.likeCount}人赞同了该回答` }}</div>
        <div class="content">
            {{ post.content.text }}
        </div>
    </div>

</template>

<script>
import post from "./Post.vue";
import PostCard from "@/components/user/PostCard.vue";

export default {
    //组件名
    name: "post-body",
    //依赖的组件
    components: {PostCard},
    //数据
    data() {
        return {
            isLoadingPostCard: false
        }
    },
    //方法
    methods: {
        collapseFullText() {
            this.$emit("collapseFullText")
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
        // 每当 question 改变时，这个函数就会执行
        // question(newQuestion, oldQuestion) {}
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {
        post: {
            type: Object,
            require: true
        }
    }
}
</script>

<style scoped>
.hideFullText {
    font-size: 10px;
    color: #9a9da6;
    cursor: pointer;
}

.hideFullText:hover {
    color: #056de8;
}

.authorInfo .el-image {
    width: 30px;
    height: 30px;
    border-radius: 2px;
}
</style>
