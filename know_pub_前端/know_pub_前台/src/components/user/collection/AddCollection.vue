<template>
    <div class="add-collection-root">
        <div class="header">
            <h4>{{ mode === 'update' ? '更新收藏夹' : '创建新收藏夹' }}</h4>
        </div>
        <div class="body">
            <input v-model="form.title" placeholder="收藏标题"/>
            <textarea v-model="form.description" placeholder="收藏描述(可选)"></textarea>
            <el-radio-group v-model="form.isPrivate" class="body-private">
                <el-radio :label="0"><span>公开</span><span class="fw-lighter text-gray">所有人都可见</span>
                </el-radio>
                <el-radio :label="1"><span>私密</span><span
                        class="fw-lighter text-gray">只有你自己可以查看这个收藏夹</span></el-radio>
            </el-radio-group>
        </div>
        <div class="footer">
            <el-button @click="$emit('close')">取消</el-button>
            <el-button :disabled="form.title.length===0" type="primary" @click="submit">确认</el-button>
        </div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";

export default {
    //组件名
    name: "add-collection",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
            form: {
                title: '',
                description: '',
                isPrivate: 0
            }
        }
    },
    props: {
        mode: {
            type: String
        },
        old: {
            type: Object
        }
    }, created() {
        if (this.old) {
            this.form.title = this.old.title
            this.form.description = this.old.description
            this.form.isPrivate = this.old.isPrivate
        }
    },
    //方法
    methods: {
        submit() {
            if (this.mode === 'update') {
                http.patch('/collection/update-collection', this.form, {
                    params: {
                        collectionId: this.old.id
                    }
                }).then(res => {
                    if (res.data.code === 200) {
                        ElMessage.success({
                            message: '更新成功'
                        })
                        this.$emit('close')
                        this.$emit('refreshCollections')

                    } else {
                        ElMessage.error({
                            message: '更新失败'
                        })
                    }

                }, reason => {
                })
            } else {
                http.post('/collection/add-collection', this.form)
                    .then(res => {
                        if (res.data.code === 200) {
                            ElMessage.success({
                                message: '创建成功'
                            })
                            this.$emit('close')
                            this.$emit('refreshCollections')

                        } else {
                            ElMessage.error({
                                message: '创建失败'
                            })
                        }

                    }, reason => {
                    })
            }

        }
    },

}
</script>

<style lang="scss" scoped>
.add-collection-root {
  display: flex;
  flex-direction: column;
  gap: 1em;
  padding: 2em;

  .header {
    text-align: center;
    color: black;
  }

  .body {
    display: flex;
    flex-direction: column;
    gap: 0.5em;

    input, textarea {
      @extend %input-base;
    }

    textarea {
      min-height: 10em;
    }

    &-private {
      display: flex;
      flex-direction: column;
      align-items: start;
      gap: 0.3em;

      .el-radio {
        display: flex;

        span {
          margin-right: 0.5em;
        }
      }
    }
  }

  .footer {
    display: flex;
    gap: 0.5em;

    & > * {
      flex: 1;
    }
  }


}
</style>
