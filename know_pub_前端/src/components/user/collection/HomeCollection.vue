<template>
    <div class="home-collection-root">
        <div class="space"></div>
        <div class="main">
            <div v-if="!isLoadingCollection" class="main-header">
                <user-collection :collection="collection" :vertical="true"></user-collection>
            </div>
            <div v-if="!isLoadingCollectionItem" class="main-body">
                <div class="count">
                    <span>{{ total }}条内容</span>
                    <el-radio-group v-model="type" :size="'small'">
                        <el-radio-button :label="types.TYPE_POST">博文</el-radio-button>
                        <el-radio-button :label="types.TYPE_QUESTION">问题</el-radio-button>
                        <el-radio-button :label="types.TYPE_ARTICLE">文章</el-radio-button>
                        <el-radio-button :label="types.TYPE_TOPIC">话题</el-radio-button>
                        <el-radio-button :label="types.TYPE_COLLECTION">收藏夹</el-radio-button>
                    </el-radio-group>
                </div>
                <div v-if="type==='post'">
                    <post v-for="post in posts" :key="post.id" :post="post"></post>
                </div>
                <div v-else-if="type==='question'">

                </div>

                <el-empty v-show="collectionItems.length===0" description="这个收藏夹什么都没有"></el-empty>
                <el-pagination
                    :current-page="currentPage"
                    :hide-on-single-page="true"
                    :page-size="pageSize"
                    :total="total"
                    layout="sizes total, prev, pager, next"
                    @current-change="getCollectionItem"
                >
                </el-pagination>
            </div>
            <el-skeleton v-show="isLoadingCollection||isLoadingCollectionItem"></el-skeleton>
        </div>
        <div class="sed">
            <div class="sed-other">
                <div class="header">
                    <span>其他收藏夹</span>
                    <div>
                        <span>查看全部</span>
                        <el-icon>
                            <arrow-right></arrow-right>
                        </el-icon>
                    </div>
                </div>
                <div class="body">
                    <div v-for="other in otherCollection" :key="other.id" class="other">
                        <div class="title" @click="toOther(other)"><span>{{ other.title }}</span></div>
                        <div>
                            <span>{{ other.updateTime.substring(0, 10) }}更新</span><span>·</span>
                            <span>{{ other.collectCount }}条内容</span><span>·</span>
                            <span>{{ other.followCount }}关注</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="space"></div>
    </div>
</template>

<script>
import http from "@/utils/http/http";
import UserCollection from "@/components/user/collection/UserCollection.vue";
import Post from "@/components/home/main/post/Post.vue";
import {ArrowRight} from "@element-plus/icons-vue";


export default {
    //组件名
    name: "home-collection",
    //依赖的组件
    components: {ArrowRight, Post, UserCollection},
    //数据
    data() {
        return {
            collection: {},
            collectionItems: [],
            otherCollection: [],
            isLoadingCollection: true,
            isLoadingCollectionItem: true,
            isLoadingOtherCollection: true,
            currentPage: 1,
            pageSize: 10,
            total: 0,
            type: 'post',
            types: {
                TYPE_POST: 'post',
                TYPE_ARTICLE: 'article',
                TYPE_QUESTION: 'question',
                TYPE_TOPIC: 'topic',
                TYPE_COLLECTION: 'collection'
            },
            posts: []
        }
    },
    //方法
    methods: {
        getCollectionItem() {
            http.get('/collection/get-collection-items', {
                params: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    collectionId: this.$route.params.collectionId,
                    type: this.type
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.collectionItems = res.data.data.page
                    this.total = res.data.data.total
                    this.isLoadingCollectionItem = false
                }
            }, reason => {
            }).finally(() => {
            })
        },
        toOther(other) {
            this.$router.push({path: `/home-collection/${other.userId}/${other.id}`})

        },
        getCollection() {
            http.get('/collection/get-collection-by-id', {
                params: {
                    collectionId: this.$route.params.collectionId
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.collection = res.data.data
                    this.isLoadingCollection = false
                }
            }, reason => {
            }).finally(() => {
            })
        },
        getOther() {
            http.get('/collection/get-collections', {
                params: {
                    pageSize: 5,
                    currentPage: 1,
                    userId: this.$route.params.userId
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.otherCollection = res.data.data.page
                    this.isLoadingOtherCollection = false
                }
            }, reason => {
            }).finally(() => {
            })
        }
    },
    //挂载时执行
    mounted() {
    },
    //创建时执行
    created() {
        this.getCollection()
        this.getOther()
        this.getCollectionItem()
    },
    //侦听器
    watch: {
        $route: {
            deep: true,
            handler(newVal, oleVal) {
                this.getCollection()
                this.getOther()
                this.getCollectionItem()
                return newVal
            }
        },
        targets: {
            deep: true,
            handler(newVal, oldVal) {
                if (newVal.length > 0) {
                    switch (this.type) {
                        case "post":
                            const params = new URLSearchParams();
                            params.append('postIds', newVal.map(item => item.targetId));

                            http.get('/post/get-posts-by-ids?' + params.toString())
                                .then(res => {
                                    if (res.data.code === 200) {
                                        this.posts = res.data.data;
                                    }
                                });
                    }
                }
                return newVal
            }
        }
    }
    ,
    //计算属性
    computed: {
        targets() {
            let type = this.type
            return this.collectionItems.filter(item => item.type === type)
        }
    }
    ,
    //绑定父组件的属性
    props: {}
}
</script>

<style lang="scss" scoped>
.home-collection-root {
    flex: 1;
    display: grid;
    grid-template-columns: 2fr 8fr 2fr 1fr;
    gap: 1em;
    grid-template-rows: 1fr;

    .main {
        display: flex;
        flex-direction: column;
        gap: 1em;

        .main-header {
            display: flex;
            flex-direction: column;
            gap: 1em;
            justify-content: center;
            padding: 1em;

            @extend %card-border;

            .title {
                font-weight: bold;
                font-size: $fs-xxxlarge;
            }

            .desc {
                display: flex;
                gap: 0.5em;
                font-weight: lighter;
                color: $gray-2;
            }
        }

        .main-body {
            @extend %card-border;
            display: flex;
            flex-direction: column;
            padding: 1em;
            gap: 1em;

            .post-root {
                :deep(.card-body) {
                    padding: 0;
                    padding-bottom: 1em;
                }
            }

            .count {
                font-weight: bold;
                font-size: $fs-medium;
                display: flex;
                align-items: center;

                .el-radio-group {
                    margin-left: auto;
                }
            }
        }
    }

    .sed {
        display: flex;
        flex-direction: column;
        gap: 1em;
        font-size: $fs-small;
        height: min-content;
        @extend %card-border;
        padding: 1em;

        .sed-other {
            display: flex;
            flex-direction: column;
            gap: 1em;

            .header {
                display: flex;
                padding: 0.5em 0em;
                border-bottom: 1px solid $white-gray;

                *:last-child {
                    margin-left: auto;
                }
            }

            .body {
                display: flex;
                flex-direction: column;
                gap: 1em;

                .other {
                    display: flex;
                    flex-direction: column;

                    .title {
                        @include clickable($dark-blue);
                        font-weight: bold;
                    }

                    & > *:last-child {
                        display: flex;
                        font-weight: lighter;
                        color: $gray;
                        gap: 0.3em;

                    }
                }
            }
        }
    }
}

</style>
