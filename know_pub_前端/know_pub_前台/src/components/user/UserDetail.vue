<template>
    <div class="detail-root">
        <el-tabs v-model="currentTab" v-on:tab-click="getData">
            <el-tab-pane label="动态" lazy name="dynamic">
                <div class="pane dynamic">
                    <div class="header"><span>我的动态</span></div>
                    <transition-group name="content">
                        <div v-for="post in dynamics" :key="post.id">
                            <post :is-show-action="true" :post="post"></post>
                        </div>
                    </transition-group>
                    <div v-show="dynamics.length===0">
                        <el-empty description="什么也没有~"></el-empty>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="问题" lazy name="question">
                <div v-show="questions.length===0">
                    <el-empty description="什么也没有~"></el-empty>
                </div>
            </el-tab-pane>
            <el-tab-pane label="文章" lazy name="article">
                <div class="pane article">
                    <div class="header">
                        <div @click="$router.push({path:'/home-write/temp'})">
                            <el-icon>
                                <plus></plus>
                            </el-icon>
                            <span>发布文章</span>
                        </div>
                    </div>
                    <transition-group name="content">
                        <div v-for="article in articles" :key="article.id">
                            <post :post="article" :is-article="true"></post>
                        </div>
                    </transition-group>
                    <div v-show="articles.length===0">
                        <el-empty description="什么也没有~"></el-empty>
                    </div>
                </div>

            </el-tab-pane>
            <el-tab-pane label="收藏" lazy name="collection">
                <div class="pane collection">
                    <div class="header">
                        <span class="selected">我的收藏</span>
                        <span>我关注的收藏</span>
                        <div class="header-add" @click="showAddCollection=true">
                            <el-icon>
                                <Plus></Plus>
                            </el-icon>
                            <span>新建收藏夹</span>
                        </div>
                    </div>
                    <div class="body">
                        <user-collection v-for="collection in collections" :key="collection.id"
                                         :collection="collection" @refreshCollections="getData"></user-collection>
                    </div>
                    <div v-show="collections.length===0">
                        <el-empty description="什么也没有~"></el-empty>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="关注" lazy name="follow">
                <div v-show="follows.length===0">
                    <el-empty description="什么也没有~"></el-empty>
                </div>
            </el-tab-pane>
        </el-tabs>
        <el-dialog v-model="showAddCollection" width="35%">
            <add-collection @close="showAddCollection=false" @refreshCollections="getData"></add-collection>
        </el-dialog>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import Post from "@/components/home/main/post/Post.vue";
import {CloseBold, Collection} from "@element-plus/icons-vue";
import Plus from "@/components/icons/Plus.vue";
import AddCollection from "@/components/user/collection/AddCollection.vue";
import UserCollection from "@/components/user/collection/UserCollection.vue";

export default {
    //组件名
    name: "user-detail",
    //依赖的组件
    components: {
        UserCollection,
        AddCollection,
        Plus,
        CloseBold,
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
            currentTab: 'dynamic',
            total: 0,
            isLoading: false,
            showAddCollection: false
        }
    },
    //方法
    methods: {
        getData(pane, pe, isMerge) {
            let name = pane ? pane.paneName : this.currentTab
            console.log(isMerge)
            switch (name) {
                case 'dynamic':
                    this.getDynamics(isMerge);
                    break;
                case 'question':
                    this.getQuestions(isMerge);
                    break;
                case 'article':
                    this.getArticles(isMerge);
                    break;
                case 'collection':
                    this.getCollections(isMerge);
                    break;
                case 'follow':
                    this.getFollows(isMerge);
                    break;
            }
        },
        getDynamics(isMerge = false) {
            if (this.isLoading) {
                return
            }
            this.isLoading = true
            if (!isMerge) {
                this.currentPage = 1
            }

            http.get('/post/dynamic', {
                params: {
                    userId: this.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                if (isMerge) {
                    this.dynamics.push(...res.data.data.page);
                } else {
                    this.dynamics = res.data.data.page;
                }
                this.currentPage++
                this.total = res.data.data.total
            }).finally(() => {
                this.isLoading = false
                this.$store.commit('SET_LOAD_DATA', false)
            })
        },
        getQuestions(isMerge) {
            if (this.isLoading) {
                return
            }
            this.isLoading = true
            if (!isMerge) {
                this.currentPage = 1
            }
            http.get('/user/questions', {
                params: {
                    userId: this.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                this.questions = res.data.data;
            })
        },
        getArticles(isMerge) {
            if (this.isLoading) {
                return
            }
            this.isLoading = true
            if (!isMerge) {
                this.currentPage = 1
            }
            http.get('/article/article-list', {
                params: {
                    userId: this.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                if (isMerge) {
                    this.articles.push(...res.data.data.page);
                } else {
                    this.articles = res.data.data.page;
                }
                this.currentPage++
                this.total = res.data.data.total
            }).finally(() => {
                this.isLoading = false
                this.$store.commit('SET_LOAD_DATA', false)
            })
        },
        getCollections(isMerge) {
            if (this.isLoading) {
                return
            }
            this.isLoading = true
            if (!isMerge) {
                this.currentPage = 1
            }
            http.get('/collection/get-collections', {
                params: {
                    userId: this.userId,
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                if (isMerge) {
                    this.collections.push(...res.data.data.page)
                } else {
                    this.collections = res.data.data.page;
                }
                this.currentPage++
                this.total = res.data.data.total
            }).finally(() => {
                this.isLoading = false
                this.$store.commit('SET_LOAD_DATA', false)
            })
        },
        getFollows() {
            http.get('/user/followers', {
                params: {
                    userId: this.userId,
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
        this.getData({paneName: this.currentTab})
    },
    //侦听器
    watch: {
        $store: {
            handler() {
                if (this.$store.state.loadData) {
                    this.getData({paneName: this.currentTab}, null, true)
                }
            },
            deep: true
        }
    }
    ,
    //计算属性
    computed: {}
    ,
    //绑定父组件的属性
    props: {
        userId: {
            require: true,
            type: String
        }
    }
}
</script>

<style lang="scss" scoped>
@include fade(content, 1s, (20px, 0, 0));

.detail-root {
  padding: 5px 15px;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;

  .pane {
    display: flex;
    flex-direction: column;
    padding: 0.5em 1em;

    &.dynamic {
      .header {
        font-weight: bold;
      }
    }

    &.article {
      .header {
        display: flex;

        & > div {
          margin-left: auto;
          @include align();
          @include clickable();
          gap: 0.5em;

        }
      }
    }

    &.collection {
      flex-direction: column;
      gap: 2em;

      .header {
        display: flex;
        align-items: center;
        gap: 1em;

        & > span {
          position: relative;

          &.selected::after {
            content: '';
            background-color: $deep-blue;
            position: absolute;
            left: 0;
            right: 0;
            bottom: -0.5em;
            height: 3px;
            border-radius: 5px;
          }
        }

        &-add {
          @include align();
          @include clickEffect();
          margin-left: auto;
          gap: 0.5em;
          color: $deep-blue;
        }
      }

      .body {
        display: flex;
        flex-direction: column;
        gap: 1em;
      }
    }
  }
}

</style>
