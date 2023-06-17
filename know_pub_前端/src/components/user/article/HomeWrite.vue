<template>
    <div class="home-write-root">
        <div class="space"></div>
        <div class="edit">
            <div class="header">
                <input placeholder="请输入标题(最多100字)"/>
            </div>
            <div class="body">
                <Toolbar
                    :defaultConfig="toolbarConfig"
                    :editor="editorRef"
                    :mode="mode"
                    class="tool-bar"
                />
                <Editor
                    v-model="valueHtml"
                    :defaultConfig="editorConfig"
                    :mode="mode"
                    class="editor"
                    @onCreated="handleCreated"
                />
            </div>
            <div class="footer"></div>
        </div>
        <div class="space"></div>
    </div>
</template>

<script>
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, onMounted, ref, shallowRef} from "vue";
import http from "@/utils/http/http";

export default {
    //组件名
    name: "home-write",
    //依赖的组件
    components: {Editor, Toolbar},
    //数据
    data() {
        return {}
    }, setup() {
        let articleId = ''
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
        // 初始化 MENU_CONF 属性
        const editorConfig = {
            MENU_CONF: {}
        }
        // 其他属性...
        editorConfig.MENU_CONF['uploadImage'] = {
            // 自定义上传
            async customUpload(file, insertFn) {  // TS 语法
                // TODO 发送上传请求到服务器，服务器判断这个文章是否存在，不存在则创建，并申请oss签名，返回签名和新的文章id
                // TODO 根据签名，利用el-upload上传图片到oss,上传成功后调用插入函数，并保存文章
                let url = ''
                insertFn(url)
            }
        }
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
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
.home-write-root {
    flex: 1;
    display: grid;
    grid-template-columns: 1fr 5fr 1fr;
    grid-template-rows: 1fr;

    .edit {
        @extend %card-border;

        .header {
            display: flex;

            input {
                flex: 1;
                @extend %input-base;
                font-size: $fs-xxxlarge;
                border-left: 0;
                border-right: 0;
            }
        }

        .body {
            .editor {
                min-height: 50vh;
            }
        }
    }
}
</style>
