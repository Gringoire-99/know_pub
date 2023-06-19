<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.tagId ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="标签名" prop="name">
        <el-input v-model="dataForm.name" placeholder="标签名"></el-input>
      </el-form-item>
      <el-form-item label="标签描述" prop="description">
        <el-input v-model="dataForm.description" placeholder="标签描述"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
      </el-form-item>
      <el-form-item label="浏览数" prop="viewCount">
        <el-input v-model="dataForm.viewCount" placeholder="浏览数"></el-input>
      </el-form-item>
      <el-form-item label="问题数" prop="questionCount">
        <el-input v-model="dataForm.questionCount" placeholder="问题数"></el-input>
      </el-form-item>
      <el-form-item label="讨论数" prop="discussCount">
        <el-input v-model="dataForm.discussCount" placeholder="讨论数"></el-input>
      </el-form-item>
      <el-form-item label="收藏数" prop="collectionCount">
        <el-input v-model="dataForm.collectionCount" placeholder="收藏数"></el-input>
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
        tagId: 0,
        name: '',
        description: '',
        createTime: '',
        status: '',
        viewCount: '',
        questionCount: '',
        discussCount: '',
        collectionCount: '',
        delFlag: ''
      },
      dataRule: {
        name: [
          {required: true, message: '标签名不能为空', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '标签描述不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
        ],
        viewCount: [
          {required: true, message: '浏览数不能为空', trigger: 'blur'}
        ],
        questionCount: [
          {required: true, message: '问题数不能为空', trigger: 'blur'}
        ],
        discussCount: [
          {required: true, message: '讨论数不能为空', trigger: 'blur'}
        ],
        collectionCount: [
          {required: true, message: '收藏数不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.tagId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.tagId) {
          this.$http({
            url: this.$http.adornUrl(`/kp_admin/tag/info/${this.dataForm.tagId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.tag.name
              this.dataForm.description = data.tag.description
              this.dataForm.createTime = data.tag.createTime
              this.dataForm.status = data.tag.status
              this.dataForm.viewCount = data.tag.viewCount
              this.dataForm.questionCount = data.tag.questionCount
              this.dataForm.discussCount = data.tag.discussCount
              this.dataForm.collectionCount = data.tag.collectionCount
              this.dataForm.delFlag = data.tag.delFlag
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
            url: this.$http.adornUrl(`/kp_admin/tag/${!this.dataForm.tagId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'tagId': this.dataForm.tagId || undefined,
              'name': this.dataForm.name,
              'description': this.dataForm.description,
              'createTime': this.dataForm.createTime,
              'status': this.dataForm.status,
              'viewCount': this.dataForm.viewCount,
              'questionCount': this.dataForm.questionCount,
              'discussCount': this.dataForm.discussCount,
              'collectionCount': this.dataForm.collectionCount,
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
