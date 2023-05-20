<template>
    <div class="d-flex align-items-start flex-column">

        <div class="authorInfo ps-0 pe-0 d-flex align-items-center w-100">
            <popover
                placement="bottom"
                trigger="hover"
                :show-after="500"
                :width="430"
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
            </popover>


            <div v-if="!isAnswerPattern" class="hideFullText ms-auto d-flex align-items-center"
                 @click="collapseFullText">收起回复
                <el-icon>
                    <arrow-up></arrow-up>
                </el-icon>
            </div>
            <div v-if="isAnswerPattern" class="follow-btn ms-auto">
                <div class="text-blue d-flex pointer" @click="isCovered=!isCovered">
                    <div v-show="!isCovered">
                        <span class="">收起</span>
                        <el-icon>
                            <arrow-up></arrow-up>
                        </el-icon>
                    </div>
                    <div v-show="isCovered">
                        <span class="">展开</span>
                        <el-icon>
                            <arrow-down></arrow-down>
                        </el-icon>
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-3 mb-3 fw-lighter">{{ `${post.likeCount}人赞同了该回答` }}</div>
        <div class="d-flex flex-column">
            <span :class="{isCovered:isCovered}" class="content">{{ post.content.text }}</span>
            <div v-show="isCovered" class="cover w-100 flex-fill" @click="isCovered=!isCovered">
                <span class="w-100 d-flex justify-content-center align-self-end flex-wrap">展开阅读全文</span>
                <el-icon>
                    <arrow-down-bold></arrow-down-bold>
                </el-icon>
            </div>
        </div>
    </div>

</template>

<script>
import post from "./Post.vue";
import PostCard from "@/components/user/PostCard.vue";
import Plus from "@/components/icons/Plus.vue";
import {ArrowDown, ArrowDownBold, ArrowUp} from "@element-plus/icons-vue";
import Popover from "@/components/common/Popover.vue";

export default {
    //组件名
    name: "post-body",
    //依赖的组件
    components: {Popover, ArrowDownBold, ArrowDown, ArrowUp, Plus, PostCard},
    //数据
    data() {
        return {
            isLoadingPostCard: false,
            isCovered: false
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
        if (this.pattern === 'answer') {

        }
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
        },
        isAnswerPattern: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style scoped>
.follow-btn .el-button {
    color: #0095da;
}

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

.isCovered {
    height: 150px !important;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 8;
    -webkit-box-orient: vertical;
}

.cover {
    display: flex;
    justify-content: center;
    align-content: end;
    flex-wrap: wrap;
    height: 80px;
    position: relative;
    top: -50px;
    background: linear-gradient(0deg, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0.82) 50%, rgba(255, 255, 255, 0.3) 100%, rgba(248, 248, 248, 0) 100%);
    z-index: 2;
}

.cover {
    color: #0095da;
    cursor: pointer;
}
</style>
