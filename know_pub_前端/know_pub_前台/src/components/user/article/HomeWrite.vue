<template>
    <div class="home-write-root">
        <div class="space"></div>
        <div class="edit">
            <div class="header">
                <input v-model="article.title" placeholder="请输入标题(最多100字)"/>
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
            <div class="footer">
                <h5>发布设置</h5>
                <div class="setting">
                    <div class="image">
                        <h5>图片</h5>
                        <div>
                            <el-upload
                                    ref="coverUploadRef"
                                    v-model:file-list="listFileCover"
                                    :before-upload="beforeCoverUpload"
                                    :data="aliyun"
                                    :limit="1"
                                    :on-error="onError"
                                    :on-success="onSuccess"
                                    :show-file-list="true"
                                    action="https://know-pub.oss-cn-fuzhou.aliyuncs.com"
                                    class="cover-uploader"
                                    list-type="picture-card"
                            >
                                <el-icon class="avatar-uploader-icon">
                                    <Plus/>
                                </el-icon>
                                <span>添加文章封面</span>
                            </el-upload>
                            <el-upload
                                    ref="uploadRef"
                                    v-model:file-list="listFile"
                                    :auto-upload="false"
                                    :before-upload="beforeUpload"
                                    :data="aliyun"
                                    :limit="1"
                                    :on-error="onError"
                                    :on-success="onSuccess"
                                    :show-file-list="true"
                                    action="https://know-pub.oss-cn-fuzhou.aliyuncs.com"
                                    class="image-uploader"
                                    list-type="picture-card"
                            >
                                <el-icon class="avatar-uploader-icon">
                                    <Plus/>
                                </el-icon>
                                <span>添加文章图片</span>
                            </el-upload>
                        </div>
                    </div>
                    <div class="tag">
                        <div @click="this.showSearch=true">
                            <div class="d-flex align-items-center gap-1">
                                <el-icon>
                                    <Plus/>
                                </el-icon>
                                <transition mode="out-in" name="tag">
                                    <span v-if="!showSearch">添加话题</span>
                                    <el-input v-else v-model="tagKeyword" class="search" @focus="showDropDown=true"
                                              @focusout="showDropDown=false;this.showSearch=false"></el-input>
                                </transition>
                            </div>
                            <div :class="{showDropdown:showDropDown}" class="suggest-dropdown">
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
                            <el-tag v-for="tag in selectedTags" :key="tag" closable @close="removeTag(tag)">{{
                                tag
                                }}
                            </el-tag>
                        </div>
                    </div>
                </div>
                <div class="submit">
                    <div class="buttons">
                        <el-button @click="save">保存</el-button>
                        <el-button type="primary" @click="publish">发布</el-button>
                    </div>
                </div>

            </div>
        </div>
        <div class="space"></div>
    </div>
</template>

<script>
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, onMounted, ref, shallowRef} from "vue";
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";
import Mock from "mockjs";
import {Position} from "@element-plus/icons-vue";

const baseUrl = 'https://know-pub.oss-cn-fuzhou.aliyuncs.com/'

export default {
    //组件名
    name: "home-write",
    //依赖的组件
    components: {Position, Editor, Toolbar},
    //数据
    data() {
        return {
            aliyun: {
                expire: '',
                policy: '',
                signature: '',
                ossaccessKeyId: '',
                key: '',
            },
            listFile: [],
            article: {
                id: 'temp',
                title: '',
                content: '',
                cover: '',
                tags: ''
            },
            showSearch: false,
            showDropDown: false,
            selectedTags: [],
            tagKeyword: '',
            tags: [],
            listFileCover: [],
            isLoadingTag: false,
            showDropdown: false,
            search: {},
            dir: ''

        }
    },
    setup() {
        // 编辑器实例，必须用 shallowRef
        const editorRef = shallowRef()
        // 内容 HTML
        const valueHtml = ref('<p></p>')
        const toolbarConfig = {}
        // 初始化 MENU_CONF 属性
        const editorConfig = {
            MENU_CONF: {}
        }
        // 其他属性...

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
    methods: {
        beforeCoverUpload(rawFile) {
            this.aliyun.key = this.dir + `article${this.article.id}/cover`
            this.article.cover = baseUrl + this.aliyun.key
            if (rawFile.type !== 'image/jpeg') {
                ElMessage.error('Avatar picture must be JPG format!')
                return false
            }
            if (rawFile.size / 1024 / 1024 > 2) {
                ElMessage.error('Avatar picture size can not exceed 2MB!')
                return false
            }
            return true
        },
        save() {
            this.article.content = this.valueHtml
            this.article.tags = this.selectedTags.join('+')
            http.patch('/article/save-script', this.article).then(res => {
                if (res.data.code === 200) {
                    ElMessage.success({
                        message: '文章保存成功'
                    })
                }
            })
        },
        publish() {
            this.article.content = this.valueHtml
            this.article.tags = this.selectedTags.join('+')
            http.patch('/article/publish-article', this.article).then(res => {
                if (res.data.code === 200) {
                    ElMessage.success({
                        message: '文章发布成功'
                    })
                }
            })
        },
        beforeUpload(rawFile) {

            if (rawFile.type !== 'image/jpeg') {
                ElMessage.error('Avatar picture must be JPG format!')
                return false
            }
            if (rawFile.size / 1024 / 1024 > 2) {
                ElMessage.error('Avatar picture size can not exceed 2MB!')
                return false
            }
            return true
        },
        onSuccess() {
            ElMessage.success({
                message: '上传成功'
            })
            this.save()
        },
        onError(error) {
            console.log('上传失败')
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
            this.selectedTags = this.selectedTags.filter(tag => tag !== tagName)
        },
        getPolicy() {
            let that = this
            http.patch('/article/upload-image', {}, {
                params: {
                    articleId: that.article.id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    let policy = res.data.data.ossPolicy
                    that.article.id = res.data.data.extraData.articleId
                    if (policy) {
                        that.aliyun.expire = policy.expire
                        that.aliyun.policy = policy.policy
                        that.aliyun.signature = policy.signature
                        that.aliyun.ossaccessKeyId = policy.ossaccessKeyId
                        that.dir = policy.dir
                    } else {
                        ElMessage.error({
                            message: '获取凭证失败'
                        })
                    }
                }
            })
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.article.id = this.$route.params['articleId']
        this.getTags()
        this.getPolicy()
        let that = this
        this.editorConfig.MENU_CONF['uploadImage'] = {
            // 自定义上传
            customUpload(file, insertFn) {
                let imageId = Mock.mock('@guid')
                that.aliyun.key = that.dir + `article${that.article.id}/` + imageId
                that.$refs.uploadRef.submit();
                insertFn(baseUrl + that.aliyun.key)
            }
        }
        http.get('/article/get-article', {
            params: {
                articleId: this.article.id
            }
        }).then(res => {
            if (res.data.code === 200) {
                this.article = res.data.data
                this.valueHtml = this.article.content
            }
        })
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
      position: relative;

      .editor {
        min-height: 50vh;
      }

      border-bottom: 1px solid $white-gray;
    }

    .footer {
      display: flex;
      flex-direction: column;
      gap: 1em;
      padding: 1em;
      color: $gray;

      h5 {
        font-size: $fs-xxxlarge;
      }

      .setting {
        display: flex;
        gap: 1em;

        & > * {
          flex: 1;
        }

        .image {
          display: flex;
          flex-direction: column;
          gap: 1em;
          padding-left: 1em;

          & > * {
            display: flex;
            gap: 1em;
          }
        }

        .tag {
          position: relative;
          gap: 0.5em;
          display: flex;
          align-items: start;

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

      .submit {
        display: flex;
        align-items: center;

        .buttons {
          margin-left: auto;
          @include align();
        }


      }
    }
  }
}
</style>
