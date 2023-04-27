<template>
    <!--    TODO
            1. 关于用户的一个小卡片，展示基础用户信息，hover时展示用户的明信片postcard
            2. 主要内容，复杂类型，支持文本，图片，视频等多媒体
            3. 评论，无限滚动列表，可回复，每个回复最多
    -->


    <div class="card">
        <div class="card-body">
            <div class="card-title">
                <div class="topic">{{ post.topic }}</div>
                <div v-show="!isCollapse" class="intro d-flex  w-100">
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
                        <span class="">{{ post.content.text.slice(0, 80) }}</span>
                        <span class="full-text-btn">...阅读全文</span>
                        <el-icon class="d-inline">
                            <arrow-down/>
                        </el-icon>
                    </div>
                </div>
                <div v-show="isCollapse" class="full-text">
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

            </div>
        </div>
    </div>
</template>

<script>
import {ArrowDown, Picture} from "@element-plus/icons-vue";
import Mock from "mockjs";

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
    components: {Picture, ArrowDown},
    //数据
    data() {
        return {
            isCollapse: false,

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
            likes: Mock.mock('@integer(0,100)'),
            dislikes: Mock.mock('@integer(0,100)'),
            author: {
                id: Mock.mock('@guid'),
                name: Mock.mock('@cname'),
                avatar: Mock.mock('@image("100x100")'),
                desc: Mock.mock('@cparagraph(1,3)'),
            },
            publish_time: Mock.mock('@datetime'),
            update_time: Mock.mock('@datetime'),
        }
    },
    //方法
    methods: {},
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

}
</script>

<style scoped>
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


</style>
