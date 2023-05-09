<template>
    <div class="root card">
        <div class="card-body">
            <!--            问题名-->
            <div class="card-title">
                <div class="question">{{ post.question }}</div>
            </div>
            <!--            简短介绍-未展开时显示-->
            <div v-if="isCollapseFullText" class="intro d-flex w-100">
                <img v-if="post.content.images.length>0" :src="post.content.images[0]" alt="介绍图片"
                     class="img-fluid col-3 intro-img">

                <div class="text">
                    <span class="intro-text" v-on:click="collapseFullText">{{ post.content.text.slice(0, 80) }}</span>
                    <span class="full-text-btn" v-on:click="collapseFullText">...阅读全文</span>
                    <el-icon class="d-inline">
                        <arrow-down/>
                    </el-icon>
                </div>
            </div>
            <!--            正文-->
            <div v-if="!isCollapseFullText" class="full-text">
                <post-body :post="post" @collapseFullText="isCollapseFullText=true"></post-body>
            </div>
            <!--            操作栏-->
            <div class="card-footer d-flex align-items-center">
                <el-button class="agree-btn" type="primary">
                    <el-icon>
                        <caret-top></caret-top>
                    </el-icon>
                    <span>赞同</span>
                    {{ post.likeCount }}
                </el-button>
                <el-button class="disagree-btn" type="primary">
                    <el-icon>
                        <caret-bottom></caret-bottom>
                    </el-icon>
                </el-button>
                <div class="etc d-flex align-items-center w-100">
                    <el-button class="comments-btn" v-on:click="collapseComments">
                        <div class="d-flex justify-content-center align-items-center">
                            <el-icon>
                                <ChatDotRound/>
                            </el-icon>
                            <span>
                               {{
                                    isCollapseComments ? post.commentNumber > 0 ? `${post.commentNumber}条评论` : "添加评论" : '收起评论'
                                }}
                            </span>

                        </div>
                    </el-button>
                    <el-button class="share-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <share></share>
                            </el-icon>
                            <span>分享</span>
                        </div>
                    </el-button>
                    <el-button class="collection-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <star-filled></star-filled>
                            </el-icon>
                            <span>收藏</span>
                        </div>
                    </el-button>
                    <el-popover placement="bottom" popper-class="popover" trigger="click">
                        <template #reference>
                            <el-button class="more-btn">
                                <div class="d-flex justify-content-center align-items-center">
                                    <el-icon class="more">
                                        <MoreFilled/>
                                    </el-icon>
                                </div>

                            </el-button>
                        </template>
                        <ul class="list-group">
                            <li class="list-group-item">不喜欢</li>
                            <li class="list-group-item">举报</li>
                            <li class="list-group-item">屏蔽这个作者</li>
                        </ul>
                    </el-popover>
                    <el-button v-show="!isCollapseComments" class="hideComments" @click="collapseComments">
                        <div class=" d-flex justify-content-center align-items-center">
                            收起
                            <el-icon>
                                <arrow-down></arrow-down>
                            </el-icon>

                        </div>
                    </el-button>

                </div>
            </div>
            <!--            评论栏-->
            <div v-if="!isCollapseComments" class="card-footer">
                <post-comment></post-comment>
                <comments :post-id="post.id"></comments>
            </div>
        </div>
    </div>
</template>

<script>
import {
    ArrowDown, ArrowUp,
    ArrowUpBold,
    CaretBottom,
    CaretTop,
    Collection,
    Comment, MoreFilled,
    Picture,
    Share,
    StarFilled
} from "@element-plus/icons-vue";
import Mock from "mockjs";

import Comments from "@/components/home/main/post/comment/Comments.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";
import PostBody from "@/components/home/main/post/PostBody.vue";

/**
 * post：某个主题的一个回答，
 * 包括：
 * 1. 回答者
 * 2. 回答内容
 * 3. 回答评论
 */
export default {
    //组件名
    name: "post",
    //依赖的组件
    components: {
        MoreFilled,
        PostBody,
        ArrowUp,
        PostComment,
        StarFilled,
        Collection, Share, Comment, Comments, CaretBottom, CaretTop, ArrowUpBold, Picture, ArrowDown
    },
    //数据
    data() {
        return {
            isCollapseFullText: true,
            isCollapseComments: true,

        }
    }, //绑定父组件的属性
    props: {
        post: {
            id: Mock.mock('@guid'),
            question: Mock.mock('@ctitle(20,40)'),
            content: {
                text: Mock.mock('@cparagraph(6,20)'),
                images: Mock.mock({
                    "images|0-3": [
                        Mock.mock('@image("200x100")')
                    ]
                })['images']
            },
            likeCount: Mock.mock('@integer(0,100)'),
            dislikeCount: Mock.mock('@integer(0,100)'),
            author: {
                id: Mock.mock('@guid'),
                name: Mock.mock('@cname'),
                avatar: Mock.mock('@image("100x100")'),
                desc: Mock.mock('@cparagraph(1,3)'),
            },
            publishTime: Mock.mock('@datetime'),
            updateTime: Mock.mock('@datetime'),
            commentNumber: Mock.mock('@integer(0,100)'),
        }
    },
    //方法
    methods: {
        collapseFullText() {
            this.isCollapseFullText = !this.isCollapseFullText;
        },
        collapseComments() {
            this.isCollapseComments = !this.isCollapseComments;
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
    },
    //侦听器
    watch: {}
    ,
    //计算属性
    computed: {}
    ,

}
</script>
<style scoped>

.list-group .el-popover.el-popper {
    padding: 0
}

.list-group {
    padding: 0;
    margin: 0;
    border: none;
}

.list-group-item {
    border: none;
}

.list-group-item:hover {
    background: #f5f5f5;
}

.card-footer .etc .el-button {
    border: none;
    background: white;
    font-size: 16px;
    margin-left: 5px;
}


.card-footer .el-button .el-icon {
    font-size: 20px;
    margin-bottom: 2px;
    margin-right: 3px;
}

.disagree-btn {
    width: 35px;
    border: none;
    background: rgba(5, 109, 232, .1);
    color: #056de8;
    margin-left: 5px;
}

.disagree-btn .el-icon {
    font-size: 20px;
}

.agree-btn .el-icon {
    font-size: 20px;
}

.agree-btn {
    padding-left: 10px;
    border: none;
    background: rgba(5, 109, 232, .1);
    color: #056de8;
}

.hideComments {
    margin-left: auto !important;
}

.card-footer {
    margin-top: 15px;
    padding: 0;
    border: none;
    background-color: white;

}

.full-text-btn {
    color: #0470bd;
    cursor: pointer;
}


.question {
    /*换行*/
    word-wrap: anywhere;
    font-size: 1.3rem;
    margin-bottom: 10px;
    font-weight: bold;
}

.intro-img {
    transition: all 0.5s;
}

.intro > img {
    border-radius: 5px;
    margin-right: 20px;
    min-width: 200px;
    min-height: 100px;
    max-height: 130px;
    object-fit: cover;
}

.intro .text .el-icon {
    font-size: 20px;
    color: #0470bd;
    cursor: pointer;
    margin-left: 5px;
}

.intro .text .el-icon:hover, .intro .text .full-text-btn:hover {
    color: #0052d6;
}

.intro .text:hover {
    color: rgba(91, 88, 88, 0.86);
    cursor: pointer;
}

@media screen and (max-width: 500px) {
    .intro-img {
        flex: 1 1 auto;
        margin: 0 !important;
    }

    .intro {
        flex-wrap: wrap;
    }

    .card-footer span {
        display: none;
    }

    .card-footer .el-button {
        margin-left: 5px;
        margin-right: 5px;
        padding-right: 5px;
        padding-left: 5px;
    }

    .root, .card-body, .card-footer {
        padding: 0 !important;
    }

}

.root {
    overflow: hidden;
    border: none;
    border-bottom: 1px solid #ebebeb;
}


</style>
