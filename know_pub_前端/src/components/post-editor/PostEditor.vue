<template>
    <div class="post-editor">
        <div>
            <Toolbar
                :defaultConfig="toolbarConfig"
                :editor="editorRef"
                :mode="mode"
                class="tool-bar"
            />
            <Editor
                :defaultConfig="editorConfig"
                v-model="valueHtml"
                :mode="mode"
                style="height: 300px;"
                @onCreated="handleCreated"
            />
        </div>
        <div class="operations w-100">
            <el-button class="ms-auto" type="primary" @click="submit">提交</el-button>
        </div>
    </div>
</template>
<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {onBeforeUnmount, ref, shallowRef, onMounted} from 'vue'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";

export default {
    components: {Editor, Toolbar},
    setup() {

        // 编辑器实例，必须用 shallowRef
        const editorRef = shallowRef()

        // 内容 HTML
        const valueHtml = ref('<p></p>')

        onMounted(() => {
            setTimeout(() => {
                // 在编辑的时候使用
                valueHtml.value = '<p></p>'
            }, 1500)
        })

        const toolbarConfig = {}
        const editorConfig = {placeholder: '请输入内容...'}

        // 组件销毁时，也及时销毁编辑器
        onBeforeUnmount(() => {
            const editor = editorRef.value
            if (editor == null) return
            editor.destroy()
        })

        const handleCreated = (editor) => {
            editorRef.value = editor // 记录 editor 实例，重要！
        }
        return {
            editorRef,
            valueHtml,
            mode: 'default', // 或 'simple'
            toolbarConfig,
            editorConfig,
            handleCreated,
        };
    },
    methods: {
        submit() {
            let userInfo = this.$store.state.userInfo
            if (this.$store.state.isLogin) {
                let post = {
                    userId: userInfo.id,
                    questionId: this.questionId,
                    content: this.valueHtml
                }
                http.post('/post/add-post', post).then(res => {
                    if (res.data.code === 200) {
                        ElMessage({
                            message: '发布成功',
                            type: 'success'
                        })
                    } else {
                        ElMessage({
                            message: '发布失败',
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
    props: {
        questionId: {
            require: true,
            type: String
        }
    }
}
</script>

<style lang="scss" scoped>
.post-editor {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 2fr;
    border: 1px solid $gray;
    border-radius: 3px;

    .operations {
        padding: 10px;
        border-top: 1px solid rgba(186, 187, 188, 0.49);
        display: flex;
    }

}


</style>
<style lang="scss">

</style>
