<template>

    <div class="d-flex align-items-center justify-content-center">
        <el-image v-show="$store.state.isLogin">

        </el-image>
        <VuemojiPicker v-show="isOpenEmoji" @emojiClick="handleEmojiClick"/>
    </div>

</template>
<script>
import {VuemojiPicker} from 'vuemoji-picker'

export default {

    //组件名
    name: "post-comment",
    //依赖的组件
    components: {VuemojiPicker},
    //数据
    data() {
        return {
            isOpenEmoji: false,
        }
    },
    //方法
    methods: {
        handleEmojiClick(emoji) {
            console.log(emoji)
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        console.log(this.$store.state.isLogin)
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

</style>
