<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="0未点赞，1点赞" prop="liked">
        <el-input v-model="dataForm.liked" placeholder="0未点赞，1点赞"></el-input>
      </el-form-item>
      <el-form-item label="0未收藏，1收藏" prop="collected">
        <el-input v-model="dataForm.collected" placeholder="0未收藏，1收藏"></el-input>
      </el-form-item>
      <el-form-item label="0未点踩，1点踩" prop="disliked">
        <el-input v-model="dataForm.disliked" placeholder="0未点踩，1点踩"></el-input>
      </el-form-item>
      <el-form-item label="0未评论，1评论" prop="replied">
        <el-input v-model="dataForm.replied" placeholder="0未评论，1评论"></el-input>
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="目标id(postId)" prop="targetId">
        <el-input v-model="dataForm.targetId" placeholder="目标id(postId)"></el-input>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
      </el-form-item>
      <el-form-item label="删除标志,0:未删除,1:已删除" prop="delFlag">
        <el-input v-model="dataForm.delFlag" placeholder="删除标志,0:未删除,1:已删除"></el-input>
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
        liked: '',
        collected: '',
        disliked: '',
        replied: '',
        userId: '',
        createTime: '',
        targetId: '',
        updateTime: '',
        delFlag: ''
      },
      dataRule: {
        liked: [
          {required: true, message: '0未点赞，1点赞不能为空', trigger: 'blur'}
        ],
        collected: [
          {required: true, message: '0未收藏，1收藏不能为空', trigger: 'blur'}
        ],
        disliked: [
          {required: true, message: '0未点踩，1点踩不能为空', trigger: 'blur'}
        ],
        replied: [
          {required: true, message: '0未评论，1评论不能为空', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '用户id不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        targetId: [
          {required: true, message: '目标id(postId)不能为空', trigger: 'blur'}
        ],
        updateTime: [
          {required: true, message: '更新时间不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/kp_admin/action/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.liked = data.action.liked
              this.dataForm.collected = data.action.collected
              this.dataForm.disliked = data.action.disliked
              this.dataForm.replied = data.action.replied
              this.dataForm.userId = data.action.userId
              this.dataForm.createTime = data.action.createTime
              this.dataForm.targetId = data.action.targetId
              this.dataForm.updateTime = data.action.updateTime
              this.dataForm.delFlag = data.action.delFlag
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
            url: this.$http.adornUrl(`/kp_admin/action/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'liked': this.dataForm.liked,
              'collected': this.dataForm.collected,
              'disliked': this.dataForm.disliked,
              'replied': this.dataForm.replied,
              'userId': this.dataForm.userId,
              'createTime': this.dataForm.createTime,
              'targetId': this.dataForm.targetId,
              'updateTime': this.dataForm.updateTime,
              'delFlag': this.dataForm.delFlag
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
