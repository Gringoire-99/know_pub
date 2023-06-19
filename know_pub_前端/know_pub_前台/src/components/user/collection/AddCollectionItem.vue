<template>
    <div class="add-collection-item">
        <div class="header">
            <h5>添加收藏</h5>
            <span>请选择要添加的收藏夹</span>
        </div>
        <div class="body">
            <div v-for="collection in collections" :key="collection.id" class="collection">
                <div class="c-left">
                    <span>{{ collection.title }}
                    <el-icon
                            v-show="collection.isPrivate===1"
                    >
                        <lock></lock>
                    </el-icon></span>
                    <span>{{ collection.collectCount }}条内容</span>
                </div>
                <div class="c-right">
                    <el-button @click="addCollectionItem(collection)">收藏</el-button>
                </div>
            </div>
            <el-pagination
                    :current-page="currentPage"
                    :hide-on-single-page="true"
                    :page-size="pageSize"
                    :total="total"
                    layout="sizes total, prev, pager, next"
                    @current-change="getCollection"
            ></el-pagination>
        </div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import {Lock} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

export default {
    //组件名
    name: "add-collection-item",
    //依赖的组件
    components: {Lock},
    //数据
    data() {
        return {
            pageSize: 10,
            currentPage: 1,
            total: 0,
            collections: []
        }
    },
    //方法
    methods: {
        getCollection() {
            http.get('/collection/get-collections', {
                params: {
                    pageSize: 5,
                    currentPage: 1,
                    userId: this.$store.state.userInfo.id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.collections = res.data.data.page
                }
            }, reason => {
            }).finally(() => {
            })
        },
        addCollectionItem(collection) {
            http.post('/collection/add-collection-item', {
                collectionId: collection.id,
                targetId: this.targetId,
                type: this.type
            }).then(res => {
                if (res.data.code === 200) {
                    ElMessage.success({
                        message: '收藏成功'
                    })
                    this.$emit('close')
                } else {
                    ElMessage.error({
                        message: '收藏失败'
                    })
                }
            }, reason => {
            })
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getCollection()
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
        targetId: {
            type: String,
            require: true
        },
        type: {
            type: String,
            require: true
        }
    }
}
</script>

<style lang="scss" scoped>
.add-collection-item {
  display: flex;
  flex-direction: column;
  gap: 1em;
  padding: 2em 1em;

  .header {
    display: flex;
    flex-direction: column;
    text-align: center;

    h5 {
      font-size: $fs-xxlarge;
      font-weight: bold;
    }
  }

  .body {
    display: flex;
    flex-direction: column;
    gap: 0.5em;

    .collection {
      display: flex;
      @extend %card-border;
      padding: 1em;

      .c-left {
        display: flex;
        flex-direction: column;

        span:first-child {
          font-weight: bold;
          display: flex;
          align-items: center;
        }

        span:last-child {
          font-weight: lighter;
          color: $gray;
        }
      }

      .c-right {
        margin-left: auto;
        display: flex;
        align-items: center;

        .el-button {
          color: $dark-blue;
          border-color: $dark-blue;
        }
      }
    }
  }
}

</style>
