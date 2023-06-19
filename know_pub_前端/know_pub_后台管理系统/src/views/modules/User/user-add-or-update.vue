<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="name">
        <el-input v-model="dataForm.name" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="dataForm.password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-input v-model="dataForm.avatar" placeholder="头像"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="dataForm.description" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="发表回答数" prop="postCount">
        <el-input v-model="dataForm.postCount" placeholder="发表回答数"></el-input>
      </el-form-item>
      <el-form-item label="评论数" prop="commentCount">
        <el-input v-model="dataForm.commentCount" placeholder="评论数"></el-input>
      </el-form-item>
      <el-form-item label="点赞数" prop="likeCount">
        <el-input v-model="dataForm.likeCount" placeholder="点赞数"></el-input>
      </el-form-item>
      <el-form-item label="关注数" prop="followCount">
        <el-input v-model="dataForm.followCount" placeholder="关注数"></el-input>
      </el-form-item>
      <el-form-item label="粉丝数" prop="followerCount">
        <el-input v-model="dataForm.followerCount" placeholder="粉丝数"></el-input>
      </el-form-item>
      <el-form-item label="提问数" prop="questionCount">
        <el-input v-model="dataForm.questionCount" placeholder="提问数"></el-input>
      </el-form-item>
      <el-form-item label="回答数" prop="answerCount">
        <el-input v-model="dataForm.answerCount" placeholder="回答数"></el-input>
      </el-form-item>
      <el-form-item label="收藏数" prop="collectionCount">
        <el-input v-model="dataForm.collectionCount" placeholder="收藏数"></el-input>
      </el-form-item>
      <el-form-item label="视频数" prop="videoCount">
        <el-input v-model="dataForm.videoCount" placeholder="视频数"></el-input>
      </el-form-item>
      <el-form-item label="文章数" prop="articleCount">
        <el-input v-model="dataForm.articleCount" placeholder="文章数"></el-input>
      </el-form-item>
      <el-form-item label="状态,0:正常,1:禁用" prop="status">
        <el-input v-model="dataForm.status" placeholder="状态,0:正常,1:禁用"></el-input>
      </el-form-item>
      <el-form-item label="koin货币数" prop="koinCount">
        <el-input v-model="dataForm.koinCount" placeholder="koin货币数"></el-input>
      </el-form-item>
      <el-form-item label="经验值" prop="experience">
        <el-input v-model="dataForm.experience" placeholder="经验值"></el-input>
      </el-form-item>
      <el-form-item label="行业" prop="industry">
        <el-input v-model="dataForm.industry" placeholder="行业"></el-input>
      </el-form-item>
      <el-form-item label="公司" prop="company">
        <el-input v-model="dataForm.company" placeholder="公司"></el-input>
      </el-form-item>
      <el-form-item label="职位" prop="job">
        <el-input v-model="dataForm.job" placeholder="职位"></el-input>
      </el-form-item>
      <el-form-item label="学校" prop="school">
        <el-input v-model="dataForm.school" placeholder="学校"></el-input>
      </el-form-item>
      <el-form-item label="地区" prop="location">
        <el-input v-model="dataForm.location" placeholder="地区"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
      </el-form-item>
      <el-form-item label="简单介绍" prop="resume">
        <el-input v-model="dataForm.resume" placeholder="简单介绍"></el-input>
      </el-form-item>
      <el-form-item label="个人认证" prop="auth">
        <el-input v-model="dataForm.auth" placeholder="个人认证"></el-input>
      </el-form-item>
      <el-form-item label="背景图" prop="background">
        <el-input v-model="dataForm.background" placeholder="背景图"></el-input>
      </el-form-item>
      <el-form-item label="删除标志,0:未删除,1:已删除" prop="delFlag">
        <el-input v-model="dataForm.delFlag" placeholder="删除标志,0:未删除,1:已删除"></el-input>
      </el-form-item>
      <el-form-item label="" prop="realName">
        <el-input v-model="dataForm.realName" placeholder=""></el-input>
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
        email: '',
        phone: '',
        name: '',
        password: '',
        avatar: '',
        description: '',
        createTime: '',
        postCount: '',
        commentCount: '',
        likeCount: '',
        followCount: '',
        followerCount: '',
        questionCount: '',
        answerCount: '',
        collectionCount: '',
        videoCount: '',
        articleCount: '',
        status: '',
        koinCount: '',
        experience: '',
        industry: '',
        company: '',
        job: '',
        school: '',
        location: '',
        gender: '',
        resume: '',
        auth: '',
        background: '',
        delFlag: '',
        realName: ''
      },
      dataRule: {
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '手机号不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        avatar: [
          {required: true, message: '头像不能为空', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '描述不能为空', trigger: 'blur'}
        ],
        createTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        postCount: [
          {required: true, message: '发表回答数不能为空', trigger: 'blur'}
        ],
        commentCount: [
          {required: true, message: '评论数不能为空', trigger: 'blur'}
        ],
        likeCount: [
          {required: true, message: '点赞数不能为空', trigger: 'blur'}
        ],
        followCount: [
          {required: true, message: '关注数不能为空', trigger: 'blur'}
        ],
        followerCount: [
          {required: true, message: '粉丝数不能为空', trigger: 'blur'}
        ],
        questionCount: [
          {required: true, message: '提问数不能为空', trigger: 'blur'}
        ],
        answerCount: [
          {required: true, message: '回答数不能为空', trigger: 'blur'}
        ],
        collectionCount: [
          {required: true, message: '收藏数不能为空', trigger: 'blur'}
        ],
        videoCount: [
          {required: true, message: '视频数不能为空', trigger: 'blur'}
        ],
        articleCount: [
          {required: true, message: '文章数不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '状态,0:正常,1:禁用不能为空', trigger: 'blur'}
        ],
        koinCount: [
          {required: true, message: 'koin货币数不能为空', trigger: 'blur'}
        ],
        experience: [
          {required: true, message: '经验值不能为空', trigger: 'blur'}
        ],
        industry: [
          {required: true, message: '行业不能为空', trigger: 'blur'}
        ],
        company: [
          {required: true, message: '公司不能为空', trigger: 'blur'}
        ],
        job: [
          {required: true, message: '职位不能为空', trigger: 'blur'}
        ],
        school: [
          {required: true, message: '学校不能为空', trigger: 'blur'}
        ],
        location: [
          {required: true, message: '地区不能为空', trigger: 'blur'}
        ],
        gender: [
          {required: true, message: '性别不能为空', trigger: 'blur'}
        ],
        resume: [
          {required: true, message: '简单介绍不能为空', trigger: 'blur'}
        ],
        auth: [
          {required: true, message: '个人认证不能为空', trigger: 'blur'}
        ],
        background: [
          {required: true, message: '背景图不能为空', trigger: 'blur'}
        ],
        delFlag: [
          {required: true, message: '删除标志,0:未删除,1:已删除不能为空', trigger: 'blur'}
        ],
        realName: [
          {required: true, message: '不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/kp_admin/user/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.email = data.user.email
              this.dataForm.phone = data.user.phone
              this.dataForm.name = data.user.name
              this.dataForm.password = data.user.password
              this.dataForm.avatar = data.user.avatar
              this.dataForm.description = data.user.description
              this.dataForm.createTime = data.user.createTime
              this.dataForm.postCount = data.user.postCount
              this.dataForm.commentCount = data.user.commentCount
              this.dataForm.likeCount = data.user.likeCount
              this.dataForm.followCount = data.user.followCount
              this.dataForm.followerCount = data.user.followerCount
              this.dataForm.questionCount = data.user.questionCount
              this.dataForm.answerCount = data.user.answerCount
              this.dataForm.collectionCount = data.user.collectionCount
              this.dataForm.videoCount = data.user.videoCount
              this.dataForm.articleCount = data.user.articleCount
              this.dataForm.status = data.user.status
              this.dataForm.koinCount = data.user.koinCount
              this.dataForm.experience = data.user.experience
              this.dataForm.industry = data.user.industry
              this.dataForm.company = data.user.company
              this.dataForm.job = data.user.job
              this.dataForm.school = data.user.school
              this.dataForm.location = data.user.location
              this.dataForm.gender = data.user.gender
              this.dataForm.resume = data.user.resume
              this.dataForm.auth = data.user.auth
              this.dataForm.background = data.user.background
              this.dataForm.delFlag = data.user.delFlag
              this.dataForm.realName = data.user.realName
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
            url: this.$http.adornUrl(`/kp_admin/user/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'email': this.dataForm.email,
              'phone': this.dataForm.phone,
              'name': this.dataForm.name,
              'password': this.dataForm.password,
              'avatar': this.dataForm.avatar,
              'description': this.dataForm.description,
              'createTime': this.dataForm.createTime,
              'postCount': this.dataForm.postCount,
              'commentCount': this.dataForm.commentCount,
              'likeCount': this.dataForm.likeCount,
              'followCount': this.dataForm.followCount,
              'followerCount': this.dataForm.followerCount,
              'questionCount': this.dataForm.questionCount,
              'answerCount': this.dataForm.answerCount,
              'collectionCount': this.dataForm.collectionCount,
              'videoCount': this.dataForm.videoCount,
              'articleCount': this.dataForm.articleCount,
              'status': this.dataForm.status,
              'koinCount': this.dataForm.koinCount,
              'experience': this.dataForm.experience,
              'industry': this.dataForm.industry,
              'company': this.dataForm.company,
              'job': this.dataForm.job,
              'school': this.dataForm.school,
              'location': this.dataForm.location,
              'gender': this.dataForm.gender,
              'resume': this.dataForm.resume,
              'auth': this.dataForm.auth,
              'background': this.dataForm.background,
              'delFlag': this.dataForm.delFlag,
              'realName': this.dataForm.realName
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
