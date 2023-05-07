<template>
    <div class="root d-flex flex-column h-100">
        <div class="header d-flex flex-column flex-fill">
            <div class="background-img w-100 h-100 flex-fill">
                1
            </div>
            <div class="d-flex">
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
        </div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import Mock from "mockjs";

export default {
    //组件名
    name: "home-user",
    //依赖的组件
    components: {},
    //数据
    data() {
        return {
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
            }
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
.background-img {
    height: 200px !important;
    display: flex;
    justify-content: center;
    align-content: start;
    overflow: hidden;
    background: #dcdfe6;
}

</style>
