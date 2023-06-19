<template>
    <div :class="{vertical:vertical,small:size==='s'}" class="collection-root">
        <div class="header" @click="toCollectionDetail">
            <span>{{ collection.title }}</span>
            <el-icon v-show="collection.isPrivate===1">
                <unlock></unlock>
            </el-icon>
            <div/>
        </div>
        <div class="footer">
            <div class="footer-info">
                <span>{{ collection.updateTime.substring(0, 10) }}更新</span>
                <span>{{ collection.collectCount }} 条内容</span>
                <span>{{ collection.followCount }} 人关注</span>
            </div>
            <div v-if="this.collection.userId===this.$store.state.userInfo.id" class="footer-operations">
                <div class="operation">
                    <el-icon>
                        <chat-dot-round></chat-dot-round>
                    </el-icon>
                    <span>添加评论</span>
                </div>
                <div class="operation" @click="showUpdate=true">
                    <el-icon>
                        <edit></edit>
                    </el-icon>
                    <span>编辑</span>
                </div>
                <el-popconfirm title="确定要删除吗？" @confirm="deleteCollection">
                    <template #reference>
                        <div class="operation">
                            <el-icon>
                                <delete></delete>
                            </el-icon>
                            <span>删除</span>
                        </div>
                    </template>
                </el-popconfirm>
            </div>
        </div>
        <el-dialog v-model="showUpdate" width="35%">
            <add-collection :old="collection" mode="update" @close="showUpdate=false"
                            @refreshCollections="$emit('refreshCollections')"></add-collection>
        </el-dialog>
    </div>
</template>

<script>
import {ChatDotRound, Delete, Unlock} from "@element-plus/icons-vue";
import Edit from "@/components/icons/Edit.vue";
import http from "@/utils/http/http";
import {ElMessage} from "element-plus";
import AddCollection from "@/components/user/collection/AddCollection.vue";

export default {
    //组件名
    name: "user-collection",
    //依赖的组件
    components: {AddCollection, Delete, Edit, ChatDotRound, Unlock},
    //数据
    data() {
        return {
            showUpdate: false
        }
    },
    //方法
    methods: {
        deleteCollection() {
            http.delete('/collection/delete-collection', {
                params: {
                    collectionId: this.collection.id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    ElMessage.success({
                        message: '删除成功'
                    })
                    this.$emit('refreshCollections')
                } else {
                    ElMessage.error({
                        message: '删除失败'
                    })
                }
            }, reason => {
                console.log(reason)
            })
        },
        toCollectionDetail() {
            this.$router.push({path: `/home-collection/${this.collection.userId}/${this.collection.id}`})

        }
    },
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
    props: {
        collection: {
            type: Object,
            require: true
        },
        vertical: {
            default: false
        },
        size: {
            default: 'm'
        }
    }
}
</script>

<style lang="scss" scoped>
.collection-root {
  display: flex;
  flex-direction: column;
  gap: 1em;
  border-bottom: 1px solid $lightGray;
  padding-bottom: 1em;

  &.vertical {
    border-bottom: none;
    padding-bottom: 0;

    .footer {
      flex-direction: column;

      .footer-operations {
        justify-content: start;
      }
    }
  }

  &.small {
    font-size: $fs-small;
  }

  & > * {
    display: flex;
  }

  .header {
    align-items: center;
    font-weight: bold;
    @include clickable($black)
  }

  .footer {
    gap: 0.5em;
    color: $gray-2;
    font-size: $fs-small;

    & > * {
      flex: 1;
      display: flex;
      gap: 0.5em;
      align-items: center;
    }

    .footer-operations {
      justify-content: end;
      gap: 1em;

      .operation {
        @include clickable();
        @include align();
        color: $gray-2;
      }
    }
  }
}

</style>
