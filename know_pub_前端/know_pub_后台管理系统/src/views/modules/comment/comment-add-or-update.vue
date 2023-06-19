<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="评论内容" prop="content">
        <el-input v-model="dataForm.content" placeholder="评论内容"></el-input>
      </el-form-item>
      <el-form-item label="评论时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="评论时间"></el-input>
      </el-form-item>
      <el-form-item label="点赞数" prop="likeCount">
        <el-input v-model="dataForm.likeCount" placeholder="点赞数"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
      </el-form-item>
      <el-form-item label="评论的文章id" prop="postId">
        <el-input v-model="dataForm.postId" placeholder="评论的文章id"></el-input>
      </el-form-item>
      <el-form-item label="这条评论发布者的id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="这条评论发布者的id"></el-input>
      </el-form-item>
      <el-form-item label="根评论的id,如果自己是根评论，此条为自己的id" prop="rootCommentId">
        <el-input v-model="dataForm.rootCommentId" placeholder="根评论的id,如果自己是根评论，此条为自己的id"></el-input>
      </el-form-item>
      <el-form-item label="被回复者的名字" prop="replyToUserName">
        <el-input v-model="dataForm.replyToUserName" placeholder="被回复者的名字"></el-input>
      </el-form-item>
      <el-form-item label="被回复者的id" prop="replyToUserId">
        <el-input v-model="dataForm.replyToUserId" placeholder="被回复者的id"></el-input>
      </el-form-item>
      <el-form-item label="被回复的评论id" prop="replyToCommentId">
        <el-input v-model="dataForm.replyToCommentId" placeholder="被回复的评论id"></el-input>
      </el-form-item>
      <el-form-item label="是否是根评论,1:是,0:不是" prop="isRootComment">
        <el-input v-model="dataForm.isRootComment" placeholder="是否是根评论,1:是,0:不是"></el-input>
      </el-form-item>
      <el-form-item label="子评论数,当数量小于等于4时查出所有子评论,大于4时不查" prop="childCount">
        <el-input v-model="dataForm.childCount"
                  placeholder="子评论数,当数量小于等于4时查出所有子评论,大于4时不查"></el-input>
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
        content: '',
        createTime: '',
        likeCount: '',
        status: '',
        postId: '',
        userId: '',
        rootCommentId: '',
        replyToUserName: '',
        replyToUserId: '',
        replyToCommentId: '',
        isRootComment: '',
        childCount: '',
        delFlag: ''
      },
      dataRule: {
        content: [
          {required: true, message: '评论内容不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '评论时间不能为空', trigger: 'blur'}
        ],
        likeCount: [
          {required: true, message: '点赞数不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
        ],
        postId: [
          {required: true, message: '评论的文章id不能为空', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '这条评论发布者的id不能为空', trigger: 'blur'}
        ],
        rootCommentId: [
          {required: true, message: '根评论的id,如果自己是根评论，此条为自己的id不能为空', trigger: 'blur'}
        ],
        replyToUserName: [
          {required: true, message: '被回复者的名字不能为空', trigger: 'blur'}
        ],
        replyToUserId: [
          {required: true, message: '被回复者的id不能为空', trigger: 'blur'}
        ],
        replyToCommentId: [
          {required: true, message: '被回复的评论id不能为空', trigger: 'blur'}
        ],
        isRootComment: [
          {required: true, message: '是否是根评论,1:是,0:不是不能为空', trigger: 'blur'}
        ],
        childCount: [
          {required: true, message: '子评论数,当数量小于等于4时查出所有子评论,大于4时不查不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/kp_admin/comment/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.content = data.comment.content
              this.dataForm.createTime = data.comment.createTime
              this.dataForm.likeCount = data.comment.likeCount
              this.dataForm.status = data.comment.status
              this.dataForm.postId = data.comment.postId
              this.dataForm.userId = data.comment.userId
              this.dataForm.rootCommentId = data.comment.rootCommentId
              this.dataForm.replyToUserName = data.comment.replyToUserName
              this.dataForm.replyToUserId = data.comment.replyToUserId
              this.dataForm.replyToCommentId = data.comment.replyToCommentId
              this.dataForm.isRootComment = data.comment.isRootComment
              this.dataForm.childCount = data.comment.childCount
              this.dataForm.delFlag = data.comment.delFlag
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
            url: this.$http.adornUrl(`/kp_admin/comment/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'content': this.dataForm.content,
              'createTime': this.dataForm.createTime,
              'likeCount': this.dataForm.likeCount,
              'status': this.dataForm.status,
              'postId': this.dataForm.postId,
              'userId': this.dataForm.userId,
              'rootCommentId': this.dataForm.rootCommentId,
              'replyToUserName': this.dataForm.replyToUserName,
              'replyToUserId': this.dataForm.replyToUserId,
              'replyToCommentId': this.dataForm.replyToCommentId,
              'isRootComment': this.dataForm.isRootComment,
              'childCount': this.dataForm.childCount,
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
