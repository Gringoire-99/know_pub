<template>
    <div class=" w-100">

        <div class="body">
            <div class=" grid header w-100">
                <div></div>
                <div class="d-flex">
                    <div class="d-flex flex-column">
                        <div class="d-flex flex-fill">
                            <div class="tags">
                                <el-tag v-for="tag in question.tags" class="pointer mx-1" round size="large">
                                    {{ tag.name }}
                                </el-tag>
                            </div>

                        </div>
                        <div>
                            <div class="title fs-3 fw-bold mt-4">
                                {{ question.title }}
                            </div>
                            <div class="content">
                                <div v-if="isCollapseFullText" class="short">
                                    <span class="content-short" @click="collapseFullText">
                                        {{ question.content.substring(0, 100) + '...' }}
                                    </span>
                                    <span class="full-text-btn pointer text-blue"
                                          v-on:click="collapseFullText">展开全部</span>
                                    <el-icon class="d-inline pointer text-blue" @click="collapseFullText">
                                        <arrow-down/>
                                    </el-icon>
                                </div>
                                <div v-else class="full">
                                    <span>
                                        {{ question.content }}
                                    </span>
                                    <span class="full-text-btn pointer text-blue"
                                          v-on:click="isCollapseFullText=!isCollapseFullText">收起</span>
                                    <el-icon class="d-inline pointer text-blue">
                                        <arrow-up/>
                                    </el-icon>
                                </div>
                            </div>
                        </div>
                        <div class="operations mt-3">
                            <button class="btn bg-primary text-white">
                                <el-icon>
                                    <star-filled></star-filled>
                                </el-icon>
                                <span>关注问题</span></button>
                            <button class="btn text-blue bg-white">
                                <el-icon>
                                    <edit></edit>
                                </el-icon>
                                <span>回答</span></button>
                            <button class="btn text-blue bg-white">
                                <el-icon>
                                    <add-user></add-user>
                                </el-icon>
                                <span>邀请</span>
                            </button>
                            <div>
                                <el-icon>
                                    <like></like>
                                </el-icon>
                                <span>好问题<span>{{ question.likeCount }}</span></span>
                            </div>
                            <div>
                                <el-icon>
                                    <message></message>
                                </el-icon>
                                <span>{{ question.commentCount > 0 ? `${question.commentCount}条评论` : '添加评论' }}</span>
                            </div>
                            <div>
                                <el-icon>
                                    <share></share>
                                </el-icon>
                                <span>分享</span>
                            </div>
                            <div>
                                <el-icon>
                                    <more></more>
                                </el-icon>
                                <span>...</span>
                            </div>
                        </div>
                    </div>
                    <div class="count ms-auto d-flex">
                        <div class="flex-column me-3">
                            <span class="text-gray">关注者</span>
                            <span class="fs-5 fw-bold">{{ question.collectionCount }}</span>
                        </div>
                        <div class="flex-column">
                            <span class="text-gray">被浏览</span>
                            <span class="fs-5 fw-bold">{{ question.visitCount }}</span>
                        </div>
                    </div>
                </div>
                <div></div>
            </div>
            <div class="answers">

            </div>
        </div>

    </div>

</template>

<script>
import http from "@/utils/http/http";
import {ArrowDown, ArrowUp, Collection, More, StarFilled} from "@element-plus/icons-vue";
import Like from "@/components/icons/Like.vue";
import Write from "@/components/icons/Write.vue";
import AddUser from "@/components/icons/AddUser.vue";
import Pen from "@/components/icons/Pen.vue";
import Edit from "@/components/icons/Edit.vue";
import Message from "@/components/icons/Message.vue";

export default {
    //组件名
    name: "home-question",
    //依赖的组件
    components: {More, Message, Edit, Pen, AddUser, StarFilled, Collection, Write, Like, ArrowUp, ArrowDown},
    //数据
    data() {
        return {
            question: {
                questionId: '',
                title: '',
                content: '',
                likeCount: '',
                commentCount: '',
                answerCount: '',
                tags: {name: '', id: ''},

            },
            posts: [],
            isCollapseFullText: true,
        }
    },
    methods: {
        collapseFullText() {
            this.isCollapseFullText = !this.isCollapseFullText
        }
    },
    //挂载时执行
    mounted() {
        console.log("mounted")
    }
    ,
    //创建时执行
    created() {
        console.log("created")
        http.get('/question', {
            params: {
                questionId: this.$route.params.questionId
            }
        }).then(res => {
            this.question = res.data.data.question
            this.posts = res.data.data.posts
            console.log(this.question)
            console.log(this.posts)
        }, reason => {
            console.log(reason)
        })
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
    props: {}
}
</script>

<style scoped>
.header {
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
}

.grid {
    display: grid;
    grid-template-columns: 1fr 8fr 1fr;
    grid-template-rows: 1fr;
}

.count {
    display: flex;
    align-content: center;
    justify-content: start;
}

.count span {
    display: flex;
    align-content: center;
    justify-content: center;
}

.content-short:hover {
    cursor: pointer;
    color: gray;
}

.operations button {
    border-radius: 3px;
    border: 1px solid #0794ff;
    display: flex;
    justify-content: center;
    text-wrap: avoid;
    text-overflow: ellipsis;
    max-height: 50px;
}

.operations button * {
    align-self: center;
}

.operations button .el-icon {
    margin-right: 5px;

}

.operations {
    display: grid;
    grid-column-gap: 20px;
    grid-template-columns: auto auto auto auto auto auto auto;
    grid-template-rows: 1fr;

}

.operations div {
    display: flex;
}

.operations div * {
    align-self: center;
}

@media screen and (max-width: 1000px) {
    .grid {
        display: grid;
        grid-template-columns: 0fr 8fr 0fr;
        grid-template-rows: 1fr;
        padding: 10px
    }

    .count {
        display: none !important;
    }

    .operations {
        grid-gap: 5px;
    }
}

@media screen  and (max-width: 500px) {
    .operations button {
        font-size: 5px;
    }

    .operations button .el-icon {
        display: none;
    }

    .operations div span {
        display: none;
    }

}

</style>
