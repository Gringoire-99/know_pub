<template>
    <div v-if="!isLoading" class="post-card d-flex flex-column">
        <div class="header d-flex">
            <div class="avatar d-flex align-items-end">
                <img :src="userInfo.avatar"/>
            </div>
            <div>
                <p class="mb-0 mt-1">{{ userInfo.name }}</p>
                <p class="desc">
                    {{
                        userInfo.description.length > 10 ? userInfo.description.substring(0, 10) + "..." : userInfo.description
                    }}</p>

            </div>
        </div>
        <div class="body">
            <div class="count d-flex">
                <div class="d-flex flex-column align-items-center justify-content-center flex-fill">
                    <span>回答</span>
                    <span>{{ userInfo.postCount }}</span>
                </div>
                <div class="d-flex flex-column align-items-center justify-content-center flex-fill">
                    <span>文章</span>
                    <span>{{ userInfo.articleCount }}</span>
                </div>
                <div class="d-flex flex-column align-items-center justify-content-center flex-fill">
                    <span>关注者</span>
                    <span>{{ userInfo.followerCount }}</span>
                </div>
            </div>
            <div class="mt-4 d-flex align-items-center justify-content-center flex-fill">
                <el-button class="bg-primary text-white flex-fill">
                    <el-icon class="">
                        <svg class="icon" height="200" p-id="2617" t="1683278043463"
                             version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M1024 438.857143H585.142857V0H438.857143v438.857143H0v146.285714h438.857143v438.857143h146.285714V585.142857h438.857143z"
                                p-id="2618"></path>
                        </svg>
                    </el-icon>
                    <span class="fs-6">关注它</span>

                </el-button>
                <el-button class=" flex-fill">
                    <el-icon :size="15">
                        <svg class="icon" height="200" p-id="4103" t="1683278246405"
                             version="1.1" viewBox="0 0 1024 1024" width="200" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M821.333333 800H547.584l-86.464 96.074667a32 32 0 1 1-47.573333-42.816l96-106.666667A32 32 0 0 1 533.333333 736h288a53.333333 53.333333 0 0 0 53.333334-53.333333V234.666667a53.333333 53.333333 0 0 0-53.333334-53.333334H202.666667a53.333333 53.333333 0 0 0-53.333334 53.333334v448a53.333333 53.333333 0 0 0 53.333334 53.333333h138.666666a32 32 0 0 1 0 64H202.666667c-64.8 0-117.333333-52.533333-117.333334-117.333333V234.666667c0-64.8 52.533333-117.333333 117.333334-117.333334h618.666666c64.8 0 117.333333 52.533333 117.333334 117.333334v448c0 64.8-52.533333 117.333333-117.333334 117.333333zM704 341.333333a32 32 0 0 1 0 64H320a32 32 0 0 1 0-64h384zM512 512a32 32 0 0 1 0 64H320a32 32 0 0 1 0-64h192z"
                                fill="#000000" p-id="4104"></path>
                        </svg>
                    </el-icon>
                    <span class="fs-6">发私信</span>

                </el-button>

            </div>
        </div>
    </div>
    <div v-else>
        <el-skeleton :rows="3"></el-skeleton>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import {Plus} from "@element-plus/icons-vue";

export default {
    //组件名
    name: "post-card",
    //依赖的组件
    components: {Plus},
    //数据
    data() {
        return {
            userInfo: {
                id: 1,
                name: "张三",
                avatar: "https://img2.baidu.com/it/u=218644361,1627252895&fm=26&fmt=auto&gp=0.jpg",
                description: "这是一个人",
                postCount: 0,
                articleCount: 0,
                followerCount: 0
            },
            isLoading: true
        }
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        http.get("/user/post-card", {
            params: {
                id: this.id
            }
        }).then(res => {
            if (res.data.code === 200) {
                this.userInfo = res.data.data.userInfo;
                this.isLoading = false;
            } else alert("failed in getting user info")
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
    props: {
        id: {
            type: String,
            require: true
        }
    }
}
</script>

<style scoped>


.desc {
    color: #232121;
    font-size: 12px;
}

.post-card {
    width: 400px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
}


.post-card .avatar {
    height: 40px;
    width: 100px;
    overflow: visible;
}

.post-card .avatar img {
    height: 70px;
    width: 70px;
    border-radius: 4px;
    border: 2px solid #ebebeb;
}
</style>
