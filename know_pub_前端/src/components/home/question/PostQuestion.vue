<template>
    <div class="post-question">
        <div class="header">
            <div class="avatar">
                <img :src="$store.state.userInfo.avatar" alt="头像"/>
            </div>
            <div class="title">
                <textarea v-model="question.question" placeholder="写下你的问题"></textarea>
            </div>
        </div>
        <div class="body">
            <div class="main-content">
                <textarea v-model="question.content" placeholder="输入问题背景，条件等详细信息"></textarea>
            </div>
        </div>
        <div class="footer">
            <div class="left">
                <div class="upload">
                    <el-icon>
                        <picture-filled></picture-filled>
                    </el-icon>
                    <el-icon>
                        <upload></upload>
                    </el-icon>
                </div>
                <div class="tag">
                    <el-icon>
                        <plus></plus>
                    </el-icon>
                    <span>添加话题</span>

                </div>
            </div>
            <div class="right">
                <div class="anonymity">
                    <el-checkbox v-model="isAnonymous"></el-checkbox>
                    <span>匿名提问</span>
                </div>
                <div class="submit">
                    <el-button @click="postQuestion">发布问题</el-button>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";
import PostEditor from "@/components/post-editor/PostEditor.vue";
import Videos from "@/components/icons/Videos.vue";
import Emoji from "@/components/icons/emoji.vue";
import {PictureFilled, Upload} from "@element-plus/icons-vue";
import Plus from "@/components/icons/Plus.vue";
import ValidationUtils from "@/utils/vaildation";

export default {
    //组件名
    name: "post-question",
    //依赖的组件
    components: {Upload, Plus, PictureFilled, Emoji, Videos, PostEditor},
    //数据
    data() {
        return {
            userInfo: {},
            isAnonymous: false,
            question: {
                question: '',
                content: ''
            }
        }
    },
    //方法
    methods: {

        postQuestion() {
            let status = ValidationUtils.validate().validateEmpty(this.question.question, this.question.content).status
            if (!status) {
                ElMessage({
                    message: '不能为空',
                    type: "error"
                })
                return
            }
            http.post('/question/postQuestion', this.question).then(
                res => {
                    if (res.data.code === 200) {
                        ElMessage({
                            message: '发布成功',
                            type: "success"
                        })
                        this.$emit('cancel')
                    } else {
                        ElMessage({
                            message: '发布失败',
                            type: "error"
                        })
                    }
                },
                reason => {
                }
            )
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
    },
    //侦听器
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
.post-question {
    width: 100%;
    display: flex;
    flex-direction: column;
    grid-gap: 0.5em;
    padding: 2em;

    & > * {
        display: flex !important;
        flex-grow: 1;
    }

    .header {
        display: flex;
        align-items: center;

        .avatar {
            img {
                max-height: 3em;
                max-width: 3em;
            }
        }

        .title {
            margin-left: 1em;
            flex-grow: 1;

            textarea {
                @include clearDefault();
                padding: 0.5em;
                min-height: 3em;
                width: 100%;
            }
        }
    }

    .body {
        flex-grow: 1;
        flex-basis: 10em;

        .main-content {
            width: 100%;
            height: 100%;

            textarea {
                @include clearDefault;
                @extend %card-border;
                min-height: 10em;
                width: 100%;
                padding: 1em;
            }
        }

    }

    .footer {

        .left {
            display: flex;
            flex-direction: column;

            .el-icon {
                font-size: 1em;
                margin-left: 0.5px;
                margin-right: 0.5px;
                @include clickable();
                color: $gray;
            }

            .tag {
                @include align();
                flex-grow: 1;

                .el-icon {
                    color: $deep-blue;
                }

                span {
                    @include clickable($color: $deep-blue);
                    margin-left: 0.5em;
                }

            }
        }

        .right {
            margin-left: auto;

            .anonymity {
                @include align($jc: start);
                color: $gray;

                span {
                    margin-left: 0.5em;
                }
            }
        }
    }
}
</style>
