<template>
    <div class="d-flex align-items-start w-100">
        <img v-show="isLogin&&isShowAvatar" :src="userInfo.avatar" alt="" class="avatar"/>
        <div class="input-comment ">

            <textarea ref="textarea" v-model="comment" :placeholder="placeholder" v-on:focusin="displayFooter"
                      v-on:focusout="displayFooter"
            >
            </textarea>

            <div :class="{'show-footer':isShowFooter}" class="comment-footer">
                <div>
                    <div class="icon">
                        <el-icon>
                            <select-picture></select-picture>
                        </el-icon>
                    </div>
                    <div class="icon">
                        <el-icon>
                            <voice></voice>
                        </el-icon>
                    </div>
                    <el-popover
                        placement="bottom"
                        trigger="click"
                        width="370px"
                    >
                        <template #reference>
                            <div>
                                <el-icon class="icon" @mousedown="preventDefault">
                                    <emoji></emoji>
                                </el-icon>
                            </div>
                            <!--                      preventDefault  防止textarea失焦-->

                        </template>
                        <VuemojiPicker class="d-block" @emojiClick="handleEmojiClick" v-on:mousedown="preventDefault"/>
                    </el-popover>
                    <button class="publish" @click="postComment" @mousedown="preventDefault">发布</button>
                </div>


            </div>

        </div>
    </div>

</template>
<script>
import {VuemojiPicker} from 'vuemoji-picker'
import {Picture} from "@element-plus/icons-vue";
import SelectPicture from "@/components/icons/SelectPicture.vue";
import Voice from "@/components/icons/Voice.vue";
import Emoji from "@/components/icons/emoji.vue";
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";

export default {

    //组件名
    name: "post-comment",
    //依赖的组件
    components: {Emoji, Voice, SelectPicture, VuemojiPicker},
    //数据
    data() {
        return {
            isLogin: false,
            userInfo: {},
            comment: '',
            isShowFooter: false
        }
    },
    //方法
    methods: {
        handleEmojiClick(emoji) {
            let textarea = this.$refs.textarea
            // 在光标位置插入
            let text = this.comment
            let start = textarea.selectionStart
            this.comment = text.slice(0, start) + emoji.unicode + text.slice(start);
            // 重新设置光标位置,因为comment是响应式的，所以需要在下一次渲染后设置,文本重置之前设置光标位置是无效的
            this.$nextTick(() => {
                textarea.selectionStart = textarea.selectionEnd = start + emoji.unicode.length
                textarea.focus()
            })

        },
        displayFooter() {
            this.isShowFooter = !this.isShowFooter
        },
        preventDefault(e) {
            e.preventDefault()
        },
        postComment() {
            if (this.$store.state.isLogin) {
                let parent = this.parent
                let postId = parent.postId
                let newComment = {}
                // 有postId说明parent是评论
                console.log(parent)
                newComment.content = this.comment
                newComment.name = this.userInfo.name
                newComment.avatar = this.userInfo.avatar
                newComment.userId = this.userInfo.id
                newComment.parentId = parent.id
                newComment.replyToUserId = parent.userId

                if (postId) {
                    //     父级是根评论：需要显示回复了谁
                    newComment.postId = postId
                    newComment.replyToUserName = parent.name
                    newComment.isRootComment = 0
                } else {
                    //     父级是post
                    newComment.postId = parent.id
                    newComment.replyToUserName = ''
                    newComment.isRootComment = 1
                }
                http.post('/comment/post-comment', newComment).then(res => {
                    if (res.data.code === 200) {
                        this.$emit('load')
                        ElMessage({
                            message: '发布成功！',
                            type: 'success'
                        })
                    } else {
                        ElMessage({
                            message: '发布失败！',
                            type: 'error'
                        })
                    }
                }, reason => {
                })
            } else {
                this.$store.commit('SET_SHOW_LOGIN', true)
            }
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.isLogin = this.$store.state.isLogin
        this.userInfo = this.$store.state.userInfo
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
        isShowAvatar: {
            type: Boolean,
            default: true
        },
        placeholder: {
            type: String,
            default: '评论千万条，友善第一条'
        },
        parent: {
            type: Object,
            require: true
        }

    }
}
</script>

<style lang="scss" scoped>
@include fade(footer, 0.3s, (0, -10px, 0));
textarea {
    width: 100%;
    border: none;
    outline: none;
    border-radius: 2px;
    box-shadow: none;
    min-height: 52px;
}

.comment-footer {
    @include gridCollapse(show-footer, row, 0.2s);

    & > div {
        @include align();
        min-height: 0;
        overflow: hidden;

        .publish {
        }

        .icon {
            font-size: 30px;
            margin-right: 10px;
            cursor: pointer;
        }

    }

    button {
        font-size: 15px;
        border-radius: 2px;
        font-weight: bold;
        padding: 3px 15px;
        background-color: #54a2d5;
        color: white;
        border: none;
        outline: none;
        box-shadow: none;
        /*    最右*/
        margin-left: auto;
    }
}

.avatar {
    width: 65px !important;
    height: 65px !important;
    border-radius: 2px;
    margin-right: 10px;
    border: 1px solid #ebebeb;
}


.input-comment {
    border: 1px solid #ebebeb;
    border-radius: 2px;
    width: 100%;
    overflow-y: hidden;
    padding: 5px 10px 0px;
}


</style>
