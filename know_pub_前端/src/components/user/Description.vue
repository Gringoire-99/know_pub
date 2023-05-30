<template>
    <div class="desc">
        <div class="header">
            <h5 class="title fw-bold">个人信息</h5>
            <span class="fw-lighter">personal details and application</span>
        </div>
        <div class="body">
            <ul :class="{showEdit:showEdit}" class="desc-list">
                <li><span>昵称</span><span>{{ userInfo.name }}</span>
                    <input v-model="info.name" placeholder="请输入昵称">
                </li>
                <li><span>头像</span>
                    <img :src="userInfo.avatar" class="avatar"/>
                    <div class="uploader">
                        <el-upload
                            :data="aliyun"
                            ref="uploadRef"
                            action="https://know-pub.oss-cn-fuzhou.aliyuncs.com"
                            v-model:file-list="listFile"
                            :auto-upload="false"
                            :limit="1"
                            :show-file-list="true"
                            class="avatar-uploader"
                            drag
                            list-type="picture-card"
                            method="post"
                            :http-request="upload"

                        >

                            <el-icon class="avatar-uploader-icon">
                                <Plus/>
                            </el-icon>
                        </el-upload>
                    </div>
                </li>
                <li><span>全名</span><span>{{ userInfo.realName }}</span>
                    <input v-model="info.realName" placeholder="请输入全名">
                </li>
                <li><span>性别</span><span>{{ userInfo.gender }}</span>
                    <input v-model="info.gender" placeholder="你的性别是？">
                </li>
                <li><span>一句话描述</span><span>{{ userInfo.description }}</span>
                    <input v-model="info.description" placeholder="描述你自己">
                </li>
                <li><span>工作</span><span>{{ userInfo.job }}</span>
                    <input v-model="info.job" placeholder="你的工作">
                </li>
                <li><span>行业</span><span>{{ userInfo.industry }}</span>
                    <input v-model="info.industry" placeholder="你的行业">
                </li>
                <li><span>公司</span><span>{{ userInfo.company }}</span>
                    <input v-model="info.company" placeholder="你的公司">
                </li>
                <li><span>学校</span><span>{{ userInfo.school }}</span>
                    <input v-model="info.school" placeholder="你的学校">
                </li>
                <li><span>位置</span><span>{{ userInfo.location }}</span>
                    <input v-model="info.location" placeholder="你的位置">
                </li>
                <li><span>详细介绍</span><span>{{ userInfo.resume }}</span>
                    <input placeholder="详细描述你自己">
                </li>
                <li><span>个人认证</span><span></span>
                </li>
                <li><span>注册时间</span><span>{{ userInfo.createTime }}</span>
                </li>
            </ul>
        </div>
        <div class="footer">
            <transition name="submit">
                <el-button v-show="showEdit" type="primary" @click="submit">提交</el-button>
            </transition>
            <button :class="{showEdit:showEdit}" class="edit" @click="showEdit=!showEdit">
                {{ showEdit ? '取消修改' : '修改信息' }}
            </button>
        </div>
    </div>
</template>

<script>


import http from "@/utils/http/http";
import {ElMessage} from "element-plus";
import axios from "axios";

export default {
    data() {
        return {
            info: {
                name: '',
                avatar: '',
                realName: '',
                gender: '',
                description: '',
                job: '',
                industry: '',
                company: '',
                school: '',
                resume: '',
                location: ''
            },
            listFile: [],
            aliyun: {}, // 存签名信息
        }
    },
    methods: {
        submit() {
            this.info.avatar = this.listFile[0].url
            http.patch('/user/update', this.info).then(res => {
                if (res.data.code === 200) {
                    let policy = res.data.data.policy
                    if (policy) {
                        this.aliyun.expire = policy.expire
                        this.aliyun.policy = policy.policy
                        this.aliyun.signature = policy.signature
                        this.aliyun.ossaccessKeyId = policy.accessId
                        this.aliyun.key = policy.dir + "avatar"
                        this.$refs.uploadRef.submit();
                    }
                }
            }, reason => {
            })
        },
        upload({file}) {
            this.aliyun.file = file
            axios.post('https://know-pub.oss-cn-fuzhou.aliyuncs.com', this.aliyun).then(res => {
                console.log(res)
            }, reason => {
                console.log(reason)
            })
        },
        beforeUpload(rawFile) {
            if (rawFile.type !== 'image/jpeg') {
                ElMessage.error('Avatar picture must be JPG format!')
                return false
            } else if (rawFile.size / 1024 / 1024 > 2) {
                ElMessage.error('Avatar picture size can not exceed 2MB!')
                return false
            }
            return true
        },


    },

    created() {
        this.info.name = this.userInfo.name
        this.info.realName = this.userInfo.realName
        this.info.resume = this.userInfo.resume
        this.info.gender = this.userInfo.gender
        this.info.description = this.userInfo.description
        this.info.job = this.userInfo.job
        this.info.industry = this.userInfo.industry
        this.info.company = this.userInfo.company
        this.info.school = this.userInfo.school
        this.info.location = this.userInfo.location

    },
    mounted() {

    },
    props: {
        userInfo: {
            require: true
        },
        showEdit: {
            default: false
        }
    }
}
</script>
<style lang="scss" scoped>
@include zoom(submit, 0.2s);
.desc {
    overflow: hidden;
    padding: 1em 1em;
    display: flex;
    flex-direction: column;

    .header {
        padding: 1em;
        border-bottom: 1px solid $lightGray;
    }

    .body {
        flex-grow: 1;
        padding: 1em 1em;

        .desc-list {
            @include clearDefault;

            & > * {
                display: grid;
                grid-template-rows: 1fr;
                grid-template-columns: 1fr 2fr 0fr;
                border-bottom: 1px solid $lightGray;
                padding: 1em 0em;
                transition: grid 0.5s;

                *:first-child {
                    font-weight: bold;
                }

                *:last-child {
                    min-width: 0 !important;
                    overflow: hidden;
                }

                .avatar {
                    width: 10em;
                    object-fit: cover;
                    border-radius: 5px;
                }

                input {
                    @include clearDefault;
                    opacity: 0;
                    transition: opacity 0.5s;
                }


            }

            &.showEdit > * {
                grid-template-columns: 1fr 2fr 3fr !important;

                input {
                    border-left: 1px solid $white-gray;
                    padding-left: 1em;
                    opacity: 1;
                }
            }
        }


    }

    .footer {
        display: flex;
        padding: 0em 1em;
        min-height: 3em;

        .edit {
            @include clearDefault;
            @include clickEffect();
            color: $blue;
            margin-left: auto;
            transition: all 0.5s;
            border: 1px solid $blue;
            padding: 0em 1em;

            &.showEdit {
                border-color: $deep-blue;
                color: $deep-blue;
            }
        }
    }
}

:deep(.el-upload-list) {
    flex-wrap: nowrap;
}

:deep(.el-upload-dragger) {
    height: 100% !important;
    width: 100% !important;
    @include align();
}

:deep(.el-upload) {
    max-width: 10em !important;
    max-height: 10em !important;
}

:deep(.el-upload-list__item) {
    max-width: 10em !important;
    max-height: 10em !important;
}

</style>
