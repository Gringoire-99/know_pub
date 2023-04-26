<template>
    <!--    TODO
            1. 关于用户的一个小卡片，展示基础用户信息，hover时展示用户的明信片postcard
            2. 主要内容，复杂类型，支持文本，图片，视频等多媒体
            3. 评论，无限滚动列表，可回复，每个回复最多
    -->


    <div class="card">
        <div class="card-body">
            <div class="card-title">
                <div class="topic p-2 ">{{ post.topic }}</div>
                <div class="intro">
                    <el-image :src="post.content.images[0]">

                    </el-image>
                </div>
                <div v-show="isCollapse" class="full-text intro">
                    <div class="p-2 authorInfo">
                        <el-image :src="post.author.avatar">
                            <template #error>
                                <div class="image-slot">
                                    <el-icon>
                                        <picture-filled></picture-filled>
                                    </el-icon>
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
import {PictureFilled} from "@element-plus/icons-vue";

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
    components: {PictureFilled},
    //数据
    data() {
        return {
            isCollapse: false,

        }
    }, //绑定父组件的属性
    props: {
        post: {
            id: '',
            // 所归属的主题
            topic: '',
            // 回答内容，是复杂类型，包括文字，图片，视频...
            content: {
                text: '',
                image: [],
            },
            likes: '',
            dislikes: '',
            // 线性结构的comments,需要传给comments组件
            comments: [],
            author: {
                id: '',
                name: '',
                desc: '',
                avatar: ''
            },
            publish_time: '',
            update_time: ''

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
.authorInfo .el-image {
    width: 24px;
    height: 24px;
}

.topic {
    /*换行*/
    word-wrap: anywhere;
    font-size: 30px;
    font-weight: 600;
    color: #333;
}
</style>
