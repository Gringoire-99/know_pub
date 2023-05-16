<template>
    <div class="editor" style="border: 1px solid #ccc">
        <div>
            <Toolbar
                    :defaultConfig="toolbarConfig"
                    :editor="editorRef"
                    :mode="mode"
                    style="border-bottom: 1px solid #ccc"
            />
            <Editor
                    v-model="valueHtml"
                    :defaultConfig="editorConfig"
                    :mode="mode"
                    style="height: 500px; overflow-y: hidden;"
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
                valueHtml.value = '<p>编辑</p>'
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
            handleCreated
        };
    },
    methods: {
        submit() {
            console.log(this.valueHtml)
        }
    }
}
</script>

<style scoped>
.editor {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 2fr;
}

.operations {
    padding: 10px;
    border-top: 1px solid rgba(186, 187, 188, 0.49);
    display: flex;
}
</style>
