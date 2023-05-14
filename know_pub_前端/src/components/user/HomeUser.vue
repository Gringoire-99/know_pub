<template>
    <el-dialog v-model="showInfoDialog">
        <div class="info-full ">
            <span class="fw-bold">性别</span><span>{{ userInfo.gender }}</span>
            <span class="fw-bold">一句话介绍</span><span>{{ userInfo.description }}</span>
            <span class="fw-bold">居住地</span><span>{{ userInfo.location }}</span>
            <span class="fw-bold">所在行业</span><span>{{ userInfo.industry }}</span>
            <span class="fw-bold">职业经历</span><span>{{ userInfo.jobHistory }}</span>
            <span class="fw-bold">教育经历</span><span>{{ userInfo.school }}</span>
            <span class="fw-bold">个人认证</span><span>{{ userInfo.authentication }}</span>
        </div>
    </el-dialog>
    <div class="grid">
        <div class="space"></div>
        <div class="body-grid">
            <div class="header">
                <div :style="`background: url('${userInfo.backgroundImg}')`" class="background-img ">
                    <div class="d-flex info">
                        <div class="avatar d-flex align-items-end">
                            <div class="img">
                                <el-image :src="userInfo.avatar" alt="" class="h-100 w-100">
                                </el-image>
                            </div>
                        </div>
                        <div class="user-info">
                            <div class="detail d-flex flex-column ms-4 mt-2">
                                <span>
                                    <span class="fw-bold fs-5">{{ userInfo.name }}</span>
                                    <span
                                        class="ms-2 fs-6 fw-light">{{
                                            userInfo.description.length > 10 ? userInfo.description.substring(0, 10) + '...' : userInfo.description
                                        }}
                                    </span>
                                </span>

                                <div class="d-flex flex-column">
                                    <div class="info-short d-flex flex-column">
                                        <div class="">
                                            <div v-show="userInfo.gender!==''">
                                                <span>性别：</span>
                                                <span>{{ userInfo.gender }}</span>
                                            </div>
                                            <div v-show="userInfo.jobHistory!==''">
                                                <span>工作历史：</span>
                                                <span>{{ userInfo.jobHistory }}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="check-detail text-blue pointer"
                                         @click="showInfoDialog=!showInfoDialog">
                                        <el-icon>
                                            <arrow-down-bold></arrow-down-bold>
                                        </el-icon>
                                        <span>查看详细信息</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <button class="ms-auto align-self-center me-3 edit">编辑资料</button>

                    </div>
                </div>
                <div class="detail-space"></div>
            </div>
            <div class="detail-grid">
                <user-detail class="main"></user-detail>
                <user-secondary :userInfo="userInfo" class="secondary"></user-secondary>
            </div>
        </div>
        <div class="space"></div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import Gender from "@/components/icons/Gender.vue";
import Job from "@/components/icons/Job.vue";
import {ArrowDownBold, ArrowUpBold} from "@element-plus/icons-vue";
import UserDetail from "@/components/user/UserDetail.vue";
import UserSecondary from "@/components/user/UserSecondary.vue";

export default {
    //组件名
    name: "home-user",
    //依赖的组件
    components: {UserSecondary, UserDetail, ArrowUpBold, ArrowDownBold, Job, Gender},
    //数据
    data() {
        return {
            showInfoDialog: false,
            id: '',
            userInfo: {
                id: "",
                name: "",
                avatar: "",
                description: "",
                // 回答数
                postCount: 0,
                // 文章数
                articleCount: 0,
                // 被关注数
                followerCount: 0,
                // 提问数
                questionCount: 0,
                collectionCount: 0,
                followCount: 0,
                videoCount: 0,
                followedTopicCount: 0,
                followedQuestionCount: 0,
                // 个人简介
                industry: '',
                gender: '',
                location: '',
                school: '',
                jobHistory: '',
                resume: '',
                authentication: '',
                backgroundImg: '',
            },

        }
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.id = this.$route.params.userId
        http.get('/user/info-detail/', {
            params: {
                id: this.id
            }
        }).then(res => {
            if (res.status === 200) {
                this.userInfo = res.data.data
                console.log(this.userInfo)
            }
        }).catch(err => {
            alert("fail in HomeUser")
        })
    },
    //侦听器
    watch: {
        // 每当 question 改变时，这个函数就会执行
        // question(newQuestion, oldQuestion) {}
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style scoped>
.info-short span {
    font-size: 15px;
}

.background-img {
    background: #7d7f80 !important;
    height: 200px;
    background-position: center !important;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr;
    align-content: end;
    flex-wrap: wrap;
}

.user-info {
    position: relative;
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: 1fr 1fr;
    min-height: 0;
    transition: bottom 0.5s, bottom 0.5s, color 0.5s;
}

.detail-space {
    height: 120px;
    background-color: #ffffff;
    transition: height 0.5s;
}

.header {
    display: grid;
    grid-template-columns:  1fr;
    grid-template-rows: 1fr;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 3px 3px 0px;
    min-height: 100px;
    overflow: hidden;
}

.avatar {
    height: 100px;
    transition: all 0.5s;
}

.img {
    width: 160px;
    height: 160px;
    border: 3px solid #ffffff;
    border-radius: 3px;
    margin-left: 20px;
    transition: all 0.5s;
}

.info-short > div > div {
    display: flex;
    align-content: center;
    flex-wrap: wrap;
}

.check-detail * {
    transition: color 0.5s;
}

.info {
    height: 150px;
    position: relative;
    bottom: -200px;
    transition: bottom 0.5s;
}

.info * {
    white-space: nowrap !important;
    flex-wrap: nowrap !important;
}

.info-full {
    display: grid;
    grid-template-columns: auto auto;
    grid-column-gap: 20px;
    margin-top: 10px;
    font-size: 15px;
}

.edit {
    border: 1px solid #0794ff;
    border-radius: 2px;
    padding: 10px 20px;
    color: #0095da;
    background-color: white;
}


@media screen  and (max-width: 1200px) {
    .grid {
        grid-template-columns: 0fr 5fr 0fr !important;
    }


}

@media screen  and (max-width: 900px) {
    .detail-grid {
        grid-template-columns: 1fr 0fr !important;
    }

    .img {
        width: 150px;
        height: 150px;
    }

    .detail-space {
        height: 0;
    }

    .info {
        bottom: -75px;
    }

    .info :not(.edit) {
        color: white;
    }


}

.grid {
    display: grid;
    grid-template-columns: 1fr 6fr 1fr;
    grid-template-rows: 1fr;
    transition: grid 0.5s;
}

.body-grid {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr auto;
    grid-gap: 10px;
    transition: all 0.5s;
}

.detail-grid {
    display: grid;
    grid-template-columns: 9fr 3fr;
    grid-template-rows: 1fr;
    grid-gap: 10px;
    transition: all 0.3s;
}
</style>
