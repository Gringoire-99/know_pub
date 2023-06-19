<template>
    <div class="hq-root w-100">
        <div class="header w-100">
            <div></div>
            <div class="header-grid">
                <div class="header-main">
                    <div class="d-flex">
                        <div v-show="tags.length>0" class="tags">
                            <el-tag v-for="tag in tags" class="pointer mx-1" round size="large">
                                {{ tag }}
                            </el-tag>
                        </div>
                    </div>
                    <div v-if="isLoadingQuestion">
                        <el-skeleton :rows="3" :throttle="1000">
                        </el-skeleton>
                    </div>
                    <div v-else class="main-content">
                        <div class="title fs-3 fw-bold ">
                            {{ question.question }}
                        </div>
                        <div class="content">
                            <div v-if="isCollapseFullText" class="short">
                                    <span class="content-short" @click="collapseFullText">
                                        {{ question.content.substring(0, 100) }}
                                    </span>
                                <div v-show="!isLoadingQuestion&&question.content.length>100">
                                    <span class="full-text-btn pointer text-blue"
                                          v-on:click="collapseFullText">...展开全部</span>
                                    <el-icon class="d-inline pointer text-blue" @click="collapseFullText">
                                        <arrow-down/>
                                    </el-icon>
                                </div>

                            </div>
                            <div v-else class="full">
                                    <span>
                                        {{ question.content }}
                                    </span>
                                <span class="full-text-btn pointer text-blue"
                                      v-on:click="isCollapseFullText=!isCollapseFullText">收起</span>
                                <el-icon class="d-inline pointer text-blue">
                                    <arrow-up/>
                                </el-icon>
                            </div>
                        </div>
                    </div>
                    <div class="operations mt-3">
                        <button class="btn bg-primary text-white">
                            <el-icon>
                                <star-filled></star-filled>
                            </el-icon>
                            <span>关注问题</span></button>
                        <button class="btn text-blue bg-white" @click="showEditor=!showEditor">
                            <el-icon>
                                <edit></edit>
                            </el-icon>
                            <span>回答</span>
                        </button>
                        <button class="btn text-blue bg-white">
                            <el-icon>
                                <add-user></add-user>
                            </el-icon>
                            <span>邀请</span>
                        </button>
                        <div>
                            <el-icon>
                                <like></like>
                            </el-icon>
                            <span>好问题<span>{{ question.likeCount }}</span></span>
                        </div>
                        <div>
                            <el-icon>
                                <message></message>
                            </el-icon>
                            <span>{{ question.commentCount > 0 ? `${question.commentCount}条评论` : '添加评论' }}</span>
                        </div>
                        <div>
                            <el-icon>
                                <share></share>
                            </el-icon>
                            <span>分享</span>
                        </div>
                        <div>
                            <el-icon>
                                <more></more>
                            </el-icon>
                        </div>
                    </div>
                </div>
                <div class="count  d-flex">
                    <div class="flex-column me-3">
                        <span class="text-gray">关注者</span>
                        <span class="fs-5 fw-bold">{{ question.collectionCount }}</span>
                    </div>
                    <div class="flex-column">
                        <span class="text-gray">被浏览</span>
                        <span class="fs-5 fw-bold">{{ question.viewCount }}</span>
                    </div>
                </div>
            </div>
            <div></div>
        </div>

        <div class="body">
            <div class="space"></div>
            <div>
                <div :class="{'show-editor':showEditor}" class="editor">
                    <post-editor :questionId="this.$route.params.questionId">
                    </post-editor>
                </div>
                <div class="main">
                    <div class="post-header ps-3 border-bottom pb-3 pt-3">
                        <span class="fw-bold">{{ question.answerCount }}个回答</span>
                    </div>
                    <div v-show="isLoadingQuestion||isLoadingAnswer">
                        <el-skeleton :rows="5" :throttle="1000"></el-skeleton>
                    </div>

                    <post v-for="post in posts" :key="post.postId" :is-answer-pattern="true" :post="post">
                    </post>
                    <div v-show="posts.length===0&&!isLoadingAnswer">
                        <el-empty description="来写第一条回答吧~"></el-empty>
                    </div>
                </div>
            </div>

            <div class="sed">
                <recommended-question :question-id="this.$route.params.questionId">
                </recommended-question>
            </div>
            <div class="space"></div>
        </div>
    </div>


</template>

<script>
import http, {http_no_token} from "@/utils/http/http";
import {ArrowDown, ArrowUp, Collection, More, StarFilled} from "@element-plus/icons-vue";
import Like from "@/components/icons/Like.vue";
import Write from "@/components/icons/Write.vue";
import AddUser from "@/components/icons/AddUser.vue";
import Pen from "@/components/icons/Pen.vue";
import Edit from "@/components/icons/Edit.vue";
import Message from "@/components/icons/Message.vue";
import Post from "@/components/home/main/post/Post.vue";
import PostEditor from "@/components/post-editor/PostEditor.vue";
import RecommendedQuestion from "@/components/home/question/sed/RecommendedQuestion.vue";

export default {
    //组件名
    name: "home-question",
    //依赖的组件
    components: {
        RecommendedQuestion,
        PostEditor,
        Post, More, Message, Edit, Pen, AddUser, StarFilled, Collection, Write, Like, ArrowUp, ArrowDown
    },
    //数据
    data() {
        return {
            isLoadingQuestion: true,
            isLoadingAnswer: false,
            question: {},
            posts: [],
            isCollapseFullText: true,
            pageSize: 10,
            currentPage: 1,
            showEditor: false,
            tags: []

        }
    },
    methods: {
        collapseFullText() {
            this.isCollapseFullText = !this.isCollapseFullText
        },
        getQuestion() {
            this.isLoadingQuestion = true
            http_no_token.get('/question/detail', {
                params: {
                    questionId: this.$route.params.questionId,
                    pageSize: this.pageSize,
                    currentPage: this.currentPage

                }
            }).then(res => {
                this.question = res.data.data
            }, reason => {
            }).finally(() => {
                this.isLoadingQuestion = false
            })
        },
        getPosts(isMerge = false) {
            if (!this.isLoadingAnswer) {
                this.isLoadingAnswer = true
                if (!isMerge) {
                    this.currentPage = 1
                }
                http.get('/post/get-posts', {
                    params: {
                        pageSize: this.pageSize,
                        currentPage: this.currentPage,
                        questionId: this.$route.params.questionId
                    }
                }).then(res => {
                    if (res.data.code === 200) {
                        if (isMerge) {
                            this.posts.push(...res.data.data.page)
                        } else {
                            this.posts = res.data.data.page
                        }
                        this.currentPage++
                    }
                }, reason => {
                }).finally(() => {
                    this.isLoadingAnswer = false
                    this.$store.commit('SET_LOAD_DATA', false)
                })

            }
        }

    },
    //挂载时执行
    mounted() {
    }
    ,
    //创建时执行
    created() {
        this.getQuestion()
        this.getPosts()
        // http.patch('/question/update-view-count',{},{
        //     params:{
        //         questionId : this.$route.params['questionId']
        //     }
        // })
    },
    //侦听器
    watch: {
        question(newVal, oldVal) {
            this.tags = String(newVal.tagNames).split('+')
            return newVal
        },
        $router: {
            deep: true,
            handler(newVal, oldVal) {
                this.getQuestion()
                return newVal
            }
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

<style lang="scss" scoped>
.hq-root {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: auto auto;
  grid-gap: 10px;

  .header {
    box-shadow: rgba(50, 50, 105, 0.15) 0px 2px 5px 0px, rgba(0, 0, 0, 0.05) 0px 1px 1px 0px;
    display: grid;
    grid-template-columns: 2fr 8fr 1fr;
    grid-template-rows: 1fr;
    transition: grid 0.5s;
    padding-bottom: 20px;
    min-height: 15em;

    .header-grid {
      display: grid;
      grid-template-columns: 4fr 1fr;
      grid-template-rows: 1fr;
      transition: grid 0.5s;

      .header-main {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 1fr 2fr 1fr;
        max-height: 15em;

        .main-content {
          padding-left: 1em;
          padding-right: 1em;
        }
      }

      .operations {
        display: grid;
        grid-column-gap: 20px;
        grid-template-columns: auto auto auto auto auto auto auto;
        grid-template-rows: 1fr;

        & > div {
          @include align();

          & > * {
            @include align();
          }
        }

        button {
          border-radius: 3px;
          border: 1px solid #0794ff;
          display: flex;
          justify-content: center;
          text-wrap: avoid;
          text-overflow: ellipsis;
          max-height: 50px;

          * {
            align-self: center;
          }

          .el-icon {
            margin-right: 5px;

          }
        }

        .count {
          display: flex;
          align-content: center;
          justify-content: start;
          min-width: 0;
          overflow: hidden;

          span {
            display: flex;
            align-content: center;
            justify-content: center;
          }

        }
      }

    }


  }

  .body {
    display: grid;
    grid-template-columns: 2fr 6fr 2fr 1fr;
    grid-template-rows: 1fr;
    transition: grid 0.5s;
    grid-gap: 1em;

    .main {
      box-shadow: rgba(9, 30, 66, 0.25) 0px 4px 8px -2px, rgba(9, 30, 66, 0.08) 0px 0px 0px 1px;
      border-bottom: 1px solid $white-gray;
    }
  }

  .content-short {
    &:hover {
      cursor: pointer;
      color: gray;
    }
  }

}


@media screen and (max-width: 1000px) {
  .header {
    grid-template-columns: 1fr 8fr 1fr;
  }

  .header-grid {
    grid-template-columns: 1fr 0fr !important;
  }

  .operations {
    grid-gap: 5px;
  }
}

@media screen and (max-width: 700px) {
  .operations button {
    font-size: 5px;
  }

  .operations button .el-icon {
    display: none;
  }

  .operations div span {
    display: none;
  }
  .header {
    grid-template-columns: 0fr 8fr 0fr !important;
    padding: 1em;

    .count {
      display: none !important;
    }
  }
  .body {
    grid-template-columns: 0fr 6fr 0fr 0fr !important;
  }

}

.editor {
  @include gridCollapse(show-editor);
}

</style>
