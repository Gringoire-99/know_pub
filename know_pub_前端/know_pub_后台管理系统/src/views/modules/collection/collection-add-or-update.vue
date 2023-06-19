<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="dataForm.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="dataForm.description" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item label="收藏数" prop="collectCount">
        <el-input v-model="dataForm.collectCount" placeholder="收藏数"></el-input>
      </el-form-item>
      <el-form-item label="关注数" prop="followCount">
        <el-input v-model="dataForm.followCount" placeholder="关注数"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
      </el-form-item>
      <el-form-item label="删除标志,0:未删除,1:已删除" prop="delFlag">
        <el-input v-model="dataForm.delFlag" placeholder="删除标志,0:未删除,1:已删除"></el-input>
      </el-form-item>
      <el-form-item label="是否私有,0:公开,1:私有" prop="isPrivate">
        <el-input v-model="dataForm.isPrivate" placeholder="是否私有,0:公开,1:私有"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        userId: '',
        title: '',
        description: '',
        collectCount: '',
        followCount: '',
        createTime: '',
        updateTime: '',
        status: '',
        delFlag: '',
        isPrivate: ''
      },
      dataRule: {
        userId: [
          {required: true, message: '用户id不能为空', trigger: 'blur'}
        ],
        title: [
          {required: true, message: '标题不能为空', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '描述不能为空', trigger: 'blur'}
        ],
        collectCount: [
          {required: true, message: '收藏数不能为空', trigger: 'blur'}
        ],
        followCount: [
          {required: true, message: '关注数不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        updateTime: [
          {required: true, message: '更新时间不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
        ],
        isPrivate: [
          {required: true, message: '是否私有,0:公开,1:私有不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/kp_admin/collection/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.userId = data.collection.userId
              this.dataForm.title = data.collection.title
              this.dataForm.description = data.collection.description
              this.dataForm.collectCount = data.collection.collectCount
              this.dataForm.followCount = data.collection.followCount
              this.dataForm.createTime = data.collection.createTime
              this.dataForm.updateTime = data.collection.updateTime
              this.dataForm.status = data.collection.status
              this.dataForm.delFlag = data.collection.delFlag
              this.dataForm.isPrivate = data.collection.isPrivate
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/kp_admin/collection/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'userId': this.dataForm.userId,
              'title': this.dataForm.title,
              'description': this.dataForm.description,
              'collectCount': this.dataForm.collectCount,
              'followCount': this.dataForm.followCount,
              'createTime': this.dataForm.createTime,
              'updateTime': this.dataForm.updateTime,
              'status': this.dataForm.status,
              'delFlag': this.dataForm.delFlag,
              'isPrivate': this.dataForm.isPrivate
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
