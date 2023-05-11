<template>
    <div class="root d-flex flex-column flex-fill">
        <div class="header d-flex flex-column flex-fill w-100">
            <div class="background-img">
                <img :src="userInfo.backgroundImg" alt="用户背景图"/>

            </div>
            <div class="d-flex">
                <div class="avatar d-flex align-items-end">
                    <div class="img">
                        <el-image :src="userInfo.avatar" alt="" class="h-100 w-100">
                        </el-image>
                    </div>
                </div>
                <div class="detail d-flex flex-column ms-4 mt-2">
                    <span>
                        <span class="fw-bold fs-5">{{ userInfo.name }}</span>
                        <span
                            class="ms-2 fs-6 fw-light">{{
                                userInfo.description.length > 10 ? userInfo.description.substring(0, 10) + '...' : userInfo.description
                            }}</span>
                    </span>

                    <div class="d-flex flex-column">
                        <div v-if="isCollapseFullInfo" class="info-short d-flex flex-column">
                            <div class="">
                                <div v-show="userInfo.gender!==''">
                                    <el-icon>
                                        <gender></gender>
                                    </el-icon>
                                    <span>
                                {{ userInfo.gender }}
                            </span>
                                </div>
                                <div v-show="userInfo.jobHistory!==''">
                                    <el-icon>
                                        <job></job>
                                    </el-icon>
                                    <span>
                                {{ userInfo.jobHistory }}
                            </span>
                                </div>
                            </div>
                        </div>
                        <div v-else class="info-full ">
                            <span class="fw-bold">性别</span><span>{{ userInfo.gender }}</span>
                            <span class="fw-bold">一句话介绍</span><span>{{ userInfo.description }}</span>
                            <span class="fw-bold">居住地</span><span>{{ userInfo.location }}</span>
                            <span class="fw-bold">所在行业</span><span>{{ userInfo.industry }}</span>
                            <span class="fw-bold">职业经历</span><span>{{ userInfo.jobHistory }}</span>
                            <span class="fw-bold">教育经历</span><span>{{ userInfo.school }}</span>
                            <span class="fw-bold">个人认证</span><span>{{ userInfo.authentication }}</span>
                        </div>
                        <div class="check-detail text-blue pointer" @click="isCollapseFullInfo=!isCollapseFullInfo">
                            <el-icon v-if="isCollapseFullInfo">
                                <arrow-down-bold></arrow-down-bold>
                            </el-icon>
                            <el-icon v-else>
                                <arrow-up-bold></arrow-up-bold>
                            </el-icon>
                            <span>{{ isCollapseFullInfo ? '查看详细信息' : '收起' }}</span>
                        </div>
                    </div>

                </div>
                <button class="ms-auto align-self-end me-3 edit">编辑资料</button>
            </div>

        </div>
        <div class="d-flex">
            <user-detail class="main"></user-detail>
            <user-secondary class="secondary"></user-secondary>
        </div>
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
            isCollapseFullInfo: true,
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
        this.id = this.$route.params.id
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
.header {
    margin-bottom: 15px;
}

.info-short span {
    font-size: 15px;
}

.background-img {
    height: 200px;
    display: flex;
    justify-content: center;
    align-content: start;
    background: #6f7073 center;
    overflow: hidden;
}

.background-img img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.header {
    box-shadow: rgba(0, 0, 0, 0.15) 0px 3px 3px 0px;
    padding-bottom: 20px;
}

.avatar {
    height: 100px;
}

.img {
    width: 160px;
    height: 160px;
    border: 3px solid #ffffff;
    border-radius: 3px;
    margin-left: 20px;
}

.info-short > div > div {
    display: flex;
    align-content: center;
    flex-wrap: wrap;
}

.el-icon {
    align-self: center !important;
}

/*.info-short{*/
/*    min-height: 70px;*/
/*    position: absolute;*/
/*    transition: all 0.3s;*/
/*}*/

/*.info-full{*/
/*    min-height: 70px;*/
/*    position: absolute;*/
/*    opacity: 0;*/
/*    transition: all 0.3s;*/
/*}*/

/*.switchOpacity.info-short{*/
/*    opacity: 0;*/
/*}*/
/*.switchOpacity.info-full{*/
/*    opacity: 1;*/
/*}*/
/*.check-detail{*/
/*    position: absolute;*/
/*    */
/*}*/
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

.main {
    flex-grow: 5;
    margin-right: 15px;
}

.secondary {
    flex-grow: 1;
}
</style>
