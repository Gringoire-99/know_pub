<template>
    <div class="detail-root">
        <el-tabs v-model="currentTab" v-on:tab-click="getData">
            <el-tab-pane label="动态" lazy name="dynamic">
                <div class="header"><span>我的动态</span></div>
                <div v-for="dynamic in dynamics" :key="dynamic.post.id">
                    <post :action="dynamic.action" :post="dynamic.post"></post>
                </div>
            </el-tab-pane>
            <el-tab-pane label="问题" lazy name="question">
                问题
            </el-tab-pane>
            <el-tab-pane label="文章" lazy name="article">
                文章
            </el-tab-pane>
            <el-tab-pane label="收藏" lazy name="collection">
                收藏
            </el-tab-pane>
            <el-tab-pane label="关注" lazy name="follow">
                关注
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import Post from "@/components/home/main/post/Post.vue";

export default {
    //组件名
    name: "user-detail",
    //依赖的组件
    components: {
        Post,
    },
    //数据
    data() {
        return {
            dynamics: [],
            questions: [],
            articles: [],
            collections: [],
            follows: [],
            pageSize: 10,
            currentPage: 1,
            currentTab: 'dynamic'
        }
    },
    //方法
    methods: {
        getData(pane, e) {
            switch (pane.paneName) {
                case 'dynamic':
                    this.getDynamics();
                    break;
                case 'question':
                    this.getQuestions();
                    break;
                case 'article':
                    this.getArticles();
                    break;
                case 'collection':
                    this.getCollections();
                    break;
                case 'follow':
                    this.getFollows();
                    break;
            }
        },
        getDynamics() {
            http.get('/user/dynamics', {
                params: {
                    userId: this.$store.state.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.dynamics = res.data.data;
                console.log(this.dynamics)
            })
        },
        getQuestions() {
            http.get('/user/questions', {
                params: {
                    userId: this.$store.state.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.questions = res.data.data;
            })
        },
        getArticles() {
            http.get('/user/articles', {
                params: {
                    userId: this.$store.state.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.articles = res.data.data;
            })
        },
        getCollections() {
            http.get('/user/collections', {
                params: {
                    userId: this.$store.state.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.collections = res.data.data;
            })
        },
        getFollows() {
            http.get('/user/follow', {
                params: {
                    userId: this.$store.state.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.follows = res.data.data;
            })
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        // this.getDynamics()
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
    margin-left: 15px;
    margin-top: 10px;
    font-weight: bold;
}

.detail-root {
    padding: 5px 15px;
    box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

</style>
