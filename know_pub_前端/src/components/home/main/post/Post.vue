<template>
    <!--    TODO
            1. 关于用户的一个小卡片，展示基础用户信息，hover时展示用户的明信片postcard
            2. 主要内容，复杂类型，支持文本，图片，视频等多媒体
            3. 评论，无限滚动列表，可回复，每个回复最多
    -->


    <div class="card">
        <div class="card-body">
            <!--            主题名-->
            <div class="card-title">
                <div class="topic">{{ post.topic }}</div>
            </div>
            <!--            简短介绍-未展开时显示-->
            <div v-if="isCollapseFullText" class="intro d-flex w-100">
                <el-image v-if="post.content.images.length>0" :src="post.content.images[0]" alt=""
                          class="img-fluid col-3">
                    <template #error>
                        <div class="image-slot">
                            <el-icon>
                                <Picture/>
                            </el-icon>
                        </div>
                    </template>
                </el-image>
                <div class="text d-inline ">
                    <span class="intro-text" v-on:click="collapseFullText">{{ post.content.text.slice(0, 80) }}</span>
                    <span class="full-text-btn" v-on:click="collapseFullText">...阅读全文</span>
                    <el-icon class="d-inline">
                        <arrow-down/>
                    </el-icon>
                </div>
            </div>
            <!--            正文-->
            <div v-if="!isCollapseFullText" class="full-text">
                <div class="p-2 authorInfo">
                    <el-image :src="post.author.avatar">
                        <template #error>
                            <div class="image-slot">
                                <el-image>
                                    <Picture></Picture>
                                </el-image>
                            </div>
                        </template>
                    </el-image>
                </div>
            </div>
            <!--            操作栏-->
            <div class="card-footer d-flex align-items-center">
                <el-button class="agree-btn" type="primary">
                    <el-icon>
                        <caret-top></caret-top>
                    </el-icon>
                    赞同
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
                            {{
                                isCollapseComments ? post.commentNumber > 0 ? `${post.commentNumber}条评论` : "添加评论" : '收起评论'
                            }}
                        </div>
                    </el-button>
                    <el-button class="share-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <share></share>
                            </el-icon>
                            分享
                        </div>
                    </el-button>
                    <el-button class="collection-btn">
                        <div class="d-flex justify-content-center align-items-center">

                            <el-icon>
                                <star-filled></star-filled>
                            </el-icon>
                            收藏
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
                    <el-button v-show="!isCollapseComments" class="hideComments" v-on:click="collapseComments">
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
                <!--                嵌入在回复的comments不是完整的，只会展示10条评论 -->
                <comments :post-id="post.id"></comments>
            </div>
        </div>
    </div>
</template>

<script>
import {
    ArrowDown,
    ArrowUpBold,
    CaretBottom,
    CaretTop,
    Collection,
    Comment,
    Picture,
    Share,
    StarFilled
} from "@element-plus/icons-vue";
import Mock from "mockjs";

import Comments from "@/components/home/main/post/comment/Comments.vue";
import PostComment from "@/components/home/main/post/comment/PostComment.vue";

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
            topic: Mock.mock('@ctitle(20,40)'),
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

.card-footer .etc .el-button:hover {
    color: #040f1c;
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

.authorInfo .el-image {
    width: 24px;
    height: 24px;
    /*    圆角*/

}

.image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    background: var(--el-fill-color-light);
    color: var(--el-text-color-secondary);
    font-size: 30px;
    height: 100%;
    width: 100%;
}

.image-slot .el-icon {
    font-size: 30px;
}

.topic {
    /*换行*/
    word-wrap: anywhere;
    font-size: 26px;
    font-weight: 600;
    color: #333;
    margin-bottom: 10px;
}

.intro .el-image {
    border-radius: 5px;
    margin-right: 20px;
    width: 200px;
    height: 100px;
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
</style>
