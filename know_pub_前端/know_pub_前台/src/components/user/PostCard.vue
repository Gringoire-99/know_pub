<template>
    <transition mode="out-in" name="post-card">
        <div v-if="!isLoading" class="post-card d-flex flex-column">
            <div class="header d-flex">
                <div class="avatar d-flex align-items-end">
                    <img :src="userInfo.avatar"/>
                </div>
                <div>
                    <p class="mb-0 mt-1">{{ userInfo.name }}</p>
                    <p class="desc">
                        {{
                        userInfo.description.length > 10 ? userInfo.description.substring(0, 10) + "..." :
                        userInfo.description
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
                <div v-if="userInfo.id!==$store.state.userInfo.id"
                     class="mt-4 d-flex align-items-center justify-content-center flex-fill">
                    <el-button v-if="!userInfo.isFollowed" class="bg-primary text-white flex-fill" @click="follow">
                        <el-icon class="">
                            <Plus></Plus>
                        </el-icon>
                        <span class="fs-6">关注它</span>
                    </el-button>

                    <el-button v-else class="flex-fill" @click="follow">
                        <el-icon class="">
                            <close-bold></close-bold>
                        </el-icon>
                        <span class="fs-6">取消关注</span>
                    </el-button>
                    <el-button class=" flex-fill">
                        <el-icon :size="15">
                            <Message></Message>
                        </el-icon>
                        <span class="fs-6">发私信</span>

                    </el-button>
                </div>
                <div v-else class="mt-4 d-flex align-items-center justify-content-center flex-fill">
                    <router-link :to="{path:`/home-user/${$store.state.userInfo.id}`}">
                        <el-button class="flex-fill">
                            <el-icon class="">
                                <home-filled></home-filled>
                            </el-icon>
                            <span class="fs-6">我的主页</span>
                        </el-button>
                    </router-link>
                </div>
            </div>
        </div>
        <div v-else>
            <el-skeleton :rows="3"></el-skeleton>
        </div>
    </transition>


</template>

<script>
import {http} from "@/utils/http/http";
import {CloseBold, HomeFilled, Message, Plus, Search} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

export default {
    //组件名
    name: "post-card",
    //依赖的组件
    components: {Search, HomeFilled, CloseBold, Message, Plus},
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
    methods: {
        follow() {
            if (!this.$store.state.isLogin) {
                this.$store.commit('SET_SHOW_LOGIN', true)
                return
            }
            http.get('/user/follow', {
                params: {
                    userId: this.id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.userInfo.isFollowed = !this.userInfo.isFollowed;
                } else {
                    ElMessage({
                        message: "关注失败",
                        type: "error"
                    })
                }

            }, reason => {
                console.log(reason)
            })
        }
    },
    mounted() {
        console.log("postcard")
        http.get("/user/post-card", {
            params: {
                userId: this.id
            }
        }).then(res => {
            if (res.data.code === 200) {
                this.userInfo = res.data.data;
                this.isLoading = false;
            } else {
                ElMessage({
                    message: "获取名片失败",
                    type: "error"
                })
            }
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
<style lang="scss" scoped>
@include zoom(post-card, 0.3s, (0.05, 0.05, 0.05), (0, -10px, 0), $reverse: false);
.desc {
  color: #232121;
  font-size: 12px;
}

.post-card {
  background: white;
  width: 400px;
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 20px;

  .avatar {
    height: 40px;
    width: 100px;
    overflow: visible;

    img {
      height: 70px;
      width: 70px;
      border-radius: 4px;
      border: 2px solid #ebebeb;
    }
  }
}


</style>
