<template>
    <div class="d-flex align-items-start  w-100">
        <img v-show="isLogin" :src="userInfo.avatar" alt="" class="avatar"/>
        <div class="input-comment ">

            <textarea ref="textarea" v-model="comment" placeholder="评论千万条，友善第一条" v-on:focusin="displayFooter">
            </textarea>
            <div v-if="isShowFooter" class="comment-footer w-100 d-flex align-items-center">

                <img alt="" class="icon" src="../../../../assets/common/image.svg">
                <img alt="" class="icon" src="../../../../assets/common/voice.svg">
                <el-popover
                    placement="bottom"
                    trigger="click"
                >
                    <template #reference>
                        <!--                      preventDefault  防止textarea失焦-->
                        <img alt="" class="icon" src="../../../../assets/common/emoji.svg"
                             v-on:mousedown="preventDefault">
                    </template>
                    <VuemojiPicker @emojiClick="handleEmojiClick" v-on:mousedown="preventDefault"/>
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
            textarea.setRangeText(emoji.unicode, textarea.selectionStart, textarea.selectionEnd, 'end')

        },
        displayFooter() {
            this.isShowFooter = true
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
    props: {}
}
</script>

<style scoped>
textarea {
    width: 100%;
    border: none;
    outline: none;
    resize: none;
    border-radius: 2px;
    box-shadow: none;
    overflow-y: scroll;
    overflow-x: hidden;
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
    overflow-x: hidden;
    min-height: 50px;
    padding: 5px 10px 0px;
}

.icon {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    cursor: pointer;
}
</style>
