<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="问题/标题" prop="question">
        <el-input v-model="dataForm.question" placeholder="问题/标题"></el-input>
      </el-form-item>
      <el-form-item label="内容(富文本)" prop="content">
        <el-input v-model="dataForm.content" placeholder="内容(富文本)"></el-input>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-input v-model="dataForm.cover" placeholder="封面"></el-input>
      </el-form-item>
      <el-form-item label="图片，使用逗号分隔" prop="images">
        <el-input v-model="dataForm.images" placeholder="图片，使用逗号分隔"></el-input>
      </el-form-item>
      <el-form-item label="点赞数" prop="likeCount">
        <el-input v-model="dataForm.likeCount" placeholder="点赞数"></el-input>
      </el-form-item>
      <el-form-item label="作者id,需要根据id去user表中查找基础信息" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="作者id,需要根据id去user表中查找基础信息"></el-input>
      </el-form-item>
      <el-form-item label="发布时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="发布时间"></el-input>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
      </el-form-item>
      <el-form-item label="评论数" prop="commentCount">
        <el-input v-model="dataForm.commentCount" placeholder="评论数"></el-input>
      </el-form-item>
      <el-form-item label="回答数" prop="answerCount">
        <el-input v-model="dataForm.answerCount" placeholder="回答数"></el-input>
      </el-form-item>
      <el-form-item label="收藏数" prop="collectionCount">
        <el-input v-model="dataForm.collectionCount" placeholder="收藏数"></el-input>
      </el-form-item>
      <el-form-item label="浏览数" prop="viewCount">
        <el-input v-model="dataForm.viewCount" placeholder="浏览数"></el-input>
      </el-form-item>
      <el-form-item label="标签名，使用逗号分隔" prop="tagNames">
        <el-input v-model="dataForm.tagNames" placeholder="标签名，使用逗号分隔"></el-input>
      </el-form-item>
      <el-form-item label="删除标志,0:未删除,1:已删除" prop="delFlag">
        <el-input v-model="dataForm.delFlag" placeholder="删除标志,0:未删除,1:已删除"></el-input>
      </el-form-item>
      <el-form-item label="是否匿名，0不匿名，1匿名" prop="isAnonymous">
        <el-input v-model="dataForm.isAnonymous" placeholder="是否匿名，0不匿名，1匿名"></el-input>
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
        question: '',
        content: '',
        cover: '',
        images: '',
        likeCount: '',
        userId: '',
        createTime: '',
        updateTime: '',
        status: '',
        commentCount: '',
        answerCount: '',
        collectionCount: '',
        viewCount: '',
        tagNames: '',
        delFlag: '',
        isAnonymous: ''
      },
      dataRule: {
        question: [
          {required: true, message: '问题/标题不能为空', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '内容(富文本)不能为空', trigger: 'blur'}
        ],
        cover: [
          {required: true, message: '封面不能为空', trigger: 'blur'}
        ],
        images: [
          {required: true, message: '图片，使用逗号分隔不能为空', trigger: 'blur'}
        ],
        likeCount: [
          {required: true, message: '点赞数不能为空', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '作者id,需要根据id去user表中查找基础信息不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '发布时间不能为空', trigger: 'blur'}
        ],
        updateTime: [
          {required: true, message: '更新时间不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
        ],
        commentCount: [
          {required: true, message: '评论数不能为空', trigger: 'blur'}
        ],
        answerCount: [
          {required: true, message: '回答数不能为空', trigger: 'blur'}
        ],
        collectionCount: [
          {required: true, message: '收藏数不能为空', trigger: 'blur'}
        ],
        viewCount: [
          {required: true, message: '浏览数不能为空', trigger: 'blur'}
        ],
        tagNames: [
          {required: true, message: '标签名，使用逗号分隔不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
        ],
        isAnonymous: [
          {required: true, message: '是否匿名，0不匿名，1匿名不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/kp_admin/question/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.question = data.question.question
              this.dataForm.content = data.question.content
              this.dataForm.cover = data.question.cover
              this.dataForm.images = data.question.images
              this.dataForm.likeCount = data.question.likeCount
              this.dataForm.userId = data.question.userId
              this.dataForm.createTime = data.question.createTime
              this.dataForm.updateTime = data.question.updateTime
              this.dataForm.status = data.question.status
              this.dataForm.commentCount = data.question.commentCount
              this.dataForm.answerCount = data.question.answerCount
              this.dataForm.collectionCount = data.question.collectionCount
              this.dataForm.viewCount = data.question.viewCount
              this.dataForm.tagNames = data.question.tagNames
              this.dataForm.delFlag = data.question.delFlag
              this.dataForm.isAnonymous = data.question.isAnonymous
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
            url: this.$http.adornUrl(`/kp_admin/question/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'question': this.dataForm.question,
              'content': this.dataForm.content,
              'cover': this.dataForm.cover,
              'images': this.dataForm.images,
              'likeCount': this.dataForm.likeCount,
              'userId': this.dataForm.userId,
              'createTime': this.dataForm.createTime,
              'updateTime': this.dataForm.updateTime,
              'status': this.dataForm.status,
              'commentCount': this.dataForm.commentCount,
              'answerCount': this.dataForm.answerCount,
              'collectionCount': this.dataForm.collectionCount,
              'viewCount': this.dataForm.viewCount,
              'tagNames': this.dataForm.tagNames,
              'delFlag': this.dataForm.delFlag,
              'isAnonymous': this.dataForm.isAnonymous
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
