<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="收藏夹id" prop="collectionId">
        <el-input v-model="dataForm.collectionId" placeholder="收藏夹id"></el-input>
      </el-form-item>
      <el-form-item label="目标id" prop="targetId">
        <el-input v-model="dataForm.targetId" placeholder="目标id"></el-input>
      </el-form-item>
      <el-form-item label="枚举类型-回答 问题 文章 话题 收藏夹" prop="type">
        <el-input v-model="dataForm.type" placeholder="枚举类型-回答 问题 文章 话题 收藏夹"></el-input>
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
      </el-form-item>
      <el-form-item label="删除标志,0:未删除,1:已删除" prop="delFlag">
        <el-input v-model="dataForm.delFlag" placeholder="删除标志,0:未删除,1:已删除"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
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
        collectionId: '',
        targetId: '',
        type: '',
        userId: '',
        createTime: '',
        updateTime: '',
        delFlag: '',
        status: ''
      },
      dataRule: {
        collectionId: [
          {required: true, message: '收藏夹id不能为空', trigger: 'blur'}
        ],
        targetId: [
          {required: true, message: '目标id不能为空', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '枚举类型-回答 问题 文章 话题 收藏夹不能为空', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '用户id不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        updateTime: [
          {required: true, message: '更新时间不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/kp_admin/collectionitem/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.collectionId = data.collectionItem.collectionId
              this.dataForm.targetId = data.collectionItem.targetId
              this.dataForm.type = data.collectionItem.type
              this.dataForm.userId = data.collectionItem.userId
              this.dataForm.createTime = data.collectionItem.createTime
              this.dataForm.updateTime = data.collectionItem.updateTime
              this.dataForm.delFlag = data.collectionItem.delFlag
              this.dataForm.status = data.collectionItem.status
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
            url: this.$http.adornUrl(`/kp_admin/collectionitem/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'collectionId': this.dataForm.collectionId,
              'targetId': this.dataForm.targetId,
              'type': this.dataForm.type,
              'userId': this.dataForm.userId,
              'createTime': this.dataForm.createTime,
              'updateTime': this.dataForm.updateTime,
              'delFlag': this.dataForm.delFlag,
              'status': this.dataForm.status
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
