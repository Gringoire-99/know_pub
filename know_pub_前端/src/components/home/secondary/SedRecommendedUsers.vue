<template>
    <div class="root d-flex flex-column">
        <div class="header d-flex align-items-center">
            <el-icon>
                <add-user></add-user>
            </el-icon>
            <span class="ms-1">推荐关注</span>
        </div>
        <el-carousel arrow="never" class="mt-2" height="250px" indicator-position="outside">
            <el-carousel-item v-for="users in usersParts" class="d-flex align-items-center">
                <div class="d-flex flex-column align-items-center w-100">
                    <div v-for="user in users" :key="user.id" class="w-100 mb-2 d-flex align-items-center">
                        <el-popover
                            placement="bottom"
                            trigger="hover"
                            width="430"
                        >
                            <post-card :id="user.id"></post-card>
                            <template #reference>
                                <div class="d-flex pointer">
                                    <img :src="user.avatar" alt="" class="avatar"/>
                                    <div class="ms-1">
                                        <div>{{ user.name }}</div>
                                        <div class="reason">「{{ user.reason.substring(0, 5) }}」</div>
                                    </div>
                                </div>

                            </template>
                        </el-popover>
                        <div class="ms-auto">
                            <el-button class="follow">
                                <el-icon>
                                    <plus></plus>
                                </el-icon>
                                关注
                            </el-button>
                        </div>

                    </div>
                </div>

            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
import AddUser from "@/components/icons/AddUser.vue";
import http from "@/utils/http/http";
import Plus from "@/components/icons/Plus.vue";
import PostCard from "@/components/user/PostCard.vue";

export default {
    //组件名
    name: "sed-recommended-users",
    //依赖的组件
    components: {PostCard, Plus, AddUser},
    //数据
    data() {
        return {
            users: [],
            usersParts: [],
            pageSize: 12
        }
    },
    //方法
    methods: {},
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        http.get('/user/recommended-users', {
            params: {
                id: this.$store.state.userId,
                pageSize: this.pageSize
            }
        }).then(response => {
            if (response.data.code === 200) {
                this.users = response.data.data.users
            }
        }, reason => {
            alert("failed in getting recommended users")
        })
    },
    //侦听器
    watch: {
        users: function (newValue, oldValue) {
            let partSize = 4
            for (let i = 0; i < newValue.length; i += partSize) {
                this.usersParts.push(newValue.slice(i, i + partSize))
            }
            return newValue
        }
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
.follow {
    border: none;
    color: #056de8;
}

.root {
    margin-bottom: 10px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border-radius: 1px;
    border: 1px solid #ebebeb;
    padding: 20px;
}

.avatar {
    width: 50px;
    height: 50px;
    border-radius: 2px;
    border: 1px solid #ebebeb;
}

.reason {
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
