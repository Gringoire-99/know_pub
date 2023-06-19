<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.commentId ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="点赞人id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="点赞人id"></el-input>
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
        commentId: 0,
        userId: ''
      },
      dataRule: {
        userId: [
          {required: true, message: '点赞人id不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.commentId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.commentId) {
          this.$http({
            url: this.$http.adornUrl(`/kp_admin/commentlike/info/${this.dataForm.commentId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.userId = data.commentLike.userId
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
            url: this.$http.adornUrl(`/kp_admin/commentlike/${!this.dataForm.commentId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'commentId': this.dataForm.commentId || undefined,
              'userId': this.dataForm.userId
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
