<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.followId ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="关注人id" prop="followerId">
        <el-input v-model="dataForm.followerId" placeholder="关注人id"></el-input>
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
        followId: 0,
        followerId: ''
      },
      dataRule: {
        followerId: [
          {required: true, message: '关注人id不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.followId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.followId) {
          this.$http({
            url: this.$http.adornUrl(`/kp_admin/follow/info/${this.dataForm.followId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.followerId = data.follow.followerId
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
            url: this.$http.adornUrl(`/kp_admin/follow/${!this.dataForm.followId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'followId': this.dataForm.followId || undefined,
              'followerId': this.dataForm.followerId
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
