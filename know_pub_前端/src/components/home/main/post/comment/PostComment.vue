<template>
    <div class="d-flex align-items-start  w-100">
        <img v-show="isLogin&&isShowAvatar" :src="userInfo.avatar" alt="" class="avatar"/>
        <div class="input-comment ">

            <textarea ref="textarea" v-model="comment" placeholder="评论千万条，友善第一条" v-on:focusin="displayFooter"
                      v-on:focusout="displayFooter">
            </textarea>
            <div v-if="isShowFooter" class="comment-footer w-100 d-flex align-items-center mb-1">

                <img alt="" class="icon" src="../../../../../assets/common/image.svg">
                <img alt="" class="icon" src="../../../../../assets/common/voice.svg">
                <el-popover
                    placement="bottom"
                    trigger="click"
                    width="370px"
                >
                    <template #reference>
                        <!--                      preventDefault  防止textarea失焦-->
                        <img alt="" class="icon" src="../../../../../assets/common/emoji.svg"
                             v-on:mousedown="preventDefault">
                    </template>
                    <VuemojiPicker class="d-block" @emojiClick="handleEmojiClick" v-on:mousedown="preventDefault"/>
                </el-popover>
                <button class="">发布</button>

            </div>
        </div>
    </div>

</template>
<script>
import {VuemojiPicker} from 'vuemoji-picker'
import {Picture} from "@element-plus/icons-vue";

export default {

    //组件名
    name: "post-comment",
    //依赖的组件
    components: {Picture, VuemojiPicker},
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
        }
    }
}
</script>

<style scoped>
textarea {
    width: 100%;
    border: none;
    outline: none;
    border-radius: 2px;
    box-shadow: none;
    min-height: 52px;
}

.comment-footer button {
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

.icon {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    cursor: pointer;
}
</style>
