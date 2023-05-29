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
                    <div @click="this.showSearch=true">
                        <div class="d-flex align-items-center gap-1">
                            <el-icon>
                                <Plus/>
                            </el-icon>
                            <transition mode="out-in" name="tag">
                                <span v-if="!showSearch">添加话题</span>
                                <el-input v-else v-model="tagKeyword" class="search" @focus="showDropdown=true"
                                          @focusout="showDropdown=false;this.showSearch=false"></el-input>
                            </transition>
                        </div>
                        <div :class="{showDropdown:showDropdown}" class="suggest-dropdown">
                            <div>
                                <ul>
                                    <li v-for="tag in tags" :key="tag.id" @click="addTag(tag.name)">
                                        {{ tag.name }}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="tags">
                        <el-tag v-for="tag in selectedTags" :key="tag" closable @close="removeTag(tag)">{{ tag }}
                        </el-tag>
                    </div>
                </div>
            </div>
            <div class="right">
                <div class="anonymity">
                    <el-checkbox v-model="question.isAnonymous"></el-checkbox>
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
import {AddLocation, PictureFilled, Upload} from "@element-plus/icons-vue";
import Plus from "@/components/icons/Plus.vue";
import ValidationUtils from "@/utils/vaildation";

export default {
    //组件名
    name: "post-question",
    //依赖的组件
    components: {AddLocation, Upload, Plus, PictureFilled, Emoji, Videos, PostEditor},
    //数据
    data() {
        return {
            userInfo: {},
            question: {
                question: '',
                content: '',
                isAnonymous: 0,
            },
            tagKeyword: '',
            currentPage: 1,
            pageSize: 10,
            isLoadingTag: false,
            tags: [],
            showSearch: false,
            showDropdown: false,
            selectedTags: [],
            search: {}
        }
    },
    //方法
    methods: {
        postQuestion() {
            console.log(this.question.isAnonymous)
            let status = ValidationUtils.validate().validateEmpty(this.question.question, this.question.content).status
            if (!status) {
                ElMessage({
                    message: '不能为空',
                    type: "error"
                })
                return
            }
            if (this.selectedTags.length === 0) {
                ElMessage({
                    message: '至少选择一个tag',
                    type: "error"
                })
                return;
            }
            let question = this.question
            question.isAnonymous = this.question.isAnonymous ? 1 : 0
            let tags = this.selectedTags.join('+')
            question.tagNames = tags
            http.post('/question/post-question', question).then(
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
        },
        getTags(isMerge = false) {
            if (this.search) {
                clearTimeout(this.search)
                this.search = setTimeout(() => {
                    if (this.isLoadingTag) return
                    this.isLoadingTag = true
                    if (!isMerge) {
                        this.currentPage = 1
                    }
                    http.get('/tag/tags', {
                        params: {
                            currentPage: this.currentPage,
                            pageSize: this.pageSize,
                            keyword: this.tagKeyword,
                        }
                    }).then(res => {
                        if (res.data.code === 200) {
                            if (isMerge) {
                                this.tags.push(...res.data.data.page)
                            } else {
                                this.tags = res.data.data.page
                            }
                        } else {
                            ElMessage({
                                message: '获取tags失败',
                                type: 'error'
                            })
                        }
                    }).finally(() => {
                        this.isLoadingTag = false
                    })
                }, 1000)
            }

        },
        addTag(tagName) {
            console.log(tagName)
            if (this.selectedTags.includes(tagName)) return
            if (this.selectedTags.length >= 3) {
                ElMessage({
                    message: '最多只能添加5个话题',
                    type: 'error'
                })
                return
            }
            this.selectedTags.push(tagName)
        },
        removeTag(tagName) {
            console.log(tagName)
            this.selectedTags = this.selectedTags.filter(tag => tag !== tagName)

        }

    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getTags()
    },
    watch: {
        tagKeyword(newVal, oldVal) {
            this.getTags()
            return newVal
        }
    },
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
@include fade(tag, 0.1s, (10px, 0, 0));
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
                resize: none;
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
                position: absolute;
                bottom: 2em;
                @include align();
                gap: 0.5em;
                flex-grow: 1;

                & > * {
                    @include align();
                    gap: 0.5em;
                }

                .tags {
                }

                .el-icon {
                    color: $deep-blue;
                    @include clickable($color: $deep-blue);
                }

                span {
                    margin-left: 0.5em;
                    @include clickable($color: $deep-blue);

                }

                .search {
                    max-height: 1.5em;
                    max-width: 10em;
                }

                .suggest-dropdown {
                    position: absolute;
                    top: 2em;
                    left: 1em;
                    background: $white;
                    z-index: 100;
                    @include gridCollapse(showDropdown);
                    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
                    border-radius: 3px;

                    ul {
                        @include clearDefault;
                        min-width: 10em;
                        min-height: 10em;
                        max-height: 15em;
                        overflow-y: scroll;

                        li {
                            @include clearDefault;
                            padding: 1em;
                            width: 100%;
                            height: 100%;
                            cursor: pointer;

                            &:hover {
                                background: $white-gray;
                            }

                        }
                    }
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
