<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('kp_admin:comment:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('kp_admin:comment:delete')" :disabled="dataListSelections.length <= 0" type="danger"
                   @click="deleteHandle()">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%;"
      @selection-change="selectionChangeHandle">
      <el-table-column
        align="center"
        header-align="center"
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="评论id"
        prop="id">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="评论内容"
        prop="content">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="评论时间"
        prop="createTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="点赞数"
        prop="likeCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="状态,0:正常,1:禁用"
        prop="status">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="评论的文章id"
        prop="postId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="这条评论发布者的id"
        prop="userId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="根评论的id,如果自己是根评论，此条为自己的id"
        prop="rootCommentId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="被回复者的名字"
        prop="replyToUserName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="被回复者的id"
        prop="replyToUserId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="被回复的评论id"
        prop="replyToCommentId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="是否是根评论,1:是,0:不是"
        prop="isRootComment">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="子评论数,当数量小于等于4时查出所有子评论,大于4时不查"
        prop="childCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="删除标志,0:未删除,1:已删除"
        prop="delFlag">
      </el-table-column>
      <el-table-column
        align="center"
        fixed="right"
        header-align="center"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button size="small" type="text" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button size="small" type="text" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageIndex"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './comment-add-or-update.vue'

export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/kp_admin/comment/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/kp_admin/comment/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>
