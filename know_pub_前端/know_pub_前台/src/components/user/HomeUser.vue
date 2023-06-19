<template>
    <div class="grid">
        <div class="space"></div>
        <div class="body-grid">
            <div class="header">
                <div :style="`background: url('${userInfo.background}')`" class="background-img ">
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
                                        String(userInfo.description).length > 10 ? String(userInfo.description).substring(0, 10) + '...' : userInfo.description
                                        }}
                                    </span>
                                </span>

                                <div class="d-flex flex-column">
                                    <div class="info-short d-flex flex-column">
                                        <div class="">
                                            <div>
                                                <span>性别：</span>
                                                <span>{{ userInfo.gender }}</span>
                                            </div>
                                            <div>
                                                <span>工作历史：</span>
                                                <span>{{ userInfo.job }}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="check-detail text-blue pointer"
                                         @click='showInfoDialog = true'>
                                        <el-icon>
                                            <arrow-down-bold></arrow-down-bold>
                                        </el-icon>
                                        <span>查看详细信息</span>
                                    </div>
                                    <el-dialog v-model="showInfoDialog">
                                        <description :userInfo="this.userInfo">
                                        </description>
                                    </el-dialog>
                                </div>

                            </div>
                        </div>
                        <button class="ms-auto align-self-center me-3 edit" @click="showInfoDialog=true">编辑资料
                        </button>

                    </div>
                </div>
                <div class="detail-space"></div>
            </div>
            <div class="detail-grid">
                <user-detail :user-id="$route.params.userId" class="main"></user-detail>
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
import {ElMessage} from "element-plus";
import Description from "@/components/user/Description.vue";

export default {
    //组件名
    name: "home-user",
    //依赖的组件
    components: {Description, UserSecondary, UserDetail, ArrowUpBold, ArrowDownBold, Job, Gender},
    //数据
    data() {
        return {
            showInfoDialog: false,
            userInfo: {},
        }
    },
    //方法
    methods: {
        getUserInfo() {
            let id = this.$route.params.userId
            if (id === this.$store.state.userInfo.id) {
                this.userInfo = this.$store.state.userInfo
            } else {
                http.get('/user/info-detail', {
                    params: {
                        userId: id
                    }
                }).then(res => {
                    if (res.data.code === 200) {
                        this.userInfo = res.data.data
                    } else {
                        ElMessage({
                            message: '获取用户信息失败',
                            type: 'error'
                        })
                    }
                }, err => {
                }).finally(() => {
                })
            }
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getUserInfo()
    },
    //侦听器
    watch: {}
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
    height: 200px;
    background: #7d7f80 center !important;
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
