import Mock from "mockjs";

function getParams(url) {
    let params = {}
    let paramStr = url.match(/.*\?(.*)/)[1]
    let paramArr = paramStr.split('&')
    paramArr.forEach(param => {
        let [key, value] = param.split('=')
        params[key] = value
    })
    return params
}

function mockSetUp() {
    Mock.mock(/.*question.*/, o => {
        let param = getParams(o.url)
        let questionId = param['questionId']
        let currentPage = parseInt(param['currentPage'])
        let pageSize = parseInt(param['pageSize'])
        let question = {
            questionId: Mock.mock('@guid'),
            title: Mock.mock('@ctitle(20,40)'),
            content: Mock.mock('@cparagraph(6,20)'),
            likeCount: Mock.mock('@integer(0,100)'),
            commentCount: Mock.mock('@integer(0,100)'),
            answerCount: Mock.mock('@integer(0,100)'),
            collectionCount: Mock.mock('@integer(2000,200000)'),
            visitCount: Mock.mock('@integer(20000,2000000)'),
            tags: Mock.mock({
                "tags|1-5": [
                    {
                        "name|+1": [
                            "教育",
                            "游戏",
                            "历史",
                            "国际",
                        ],
                        "id": Mock.mock('@guid')
                    }
                ]
            })['tags']
        }
        let posts = []
        console.log(currentPage, pageSize)
        for (let i = currentPage; i < pageSize + currentPage; i++) {
            let post = {
                id: Mock.mock('@guid'),
                question: Mock.mock('@ctitle(20,40)'),
                questionId: Mock.mock('@guid'),
                content: {
                    text: Mock.mock('@cparagraph(20,100)'),
                    images: Mock.mock({
                        "images|0-3": [
                            Mock.mock('@image("200x100")')
                        ]
                    })['images'],
                    coverImage: Mock.mock('@image("200x100")'),
                },
                likeCount: Mock.mock('@integer(0,100)'),
                dislikeCount: Mock.mock('@integer(0,100)'),
                author: {
                    id: Mock.mock('@guid'),
                    name: Mock.mock('@cname'),
                    avatar: Mock.mock('@image("100x100")'),
                    desc: Mock.mock('@cparagraph(1,3)'),
                },
                publishTime: Mock.mock('@datetime'),
                updateTime: Mock.mock('@datetime'),
                commentNumber: Mock.mock('@integer(100,200)'),
            }
            posts.push(post)
        }
        return {
            code: 200,
            data: {
                question: question,
                posts: posts
            }
        }

    })
    Mock.mock(/.*user\/dynamics.*/, o => {
        /**
         * 动态：userId+postId+动作
         * 后端根据动态的postId查出对应post
         * 组成{post+动作}的数组
         *
         * */
        let param = getParams(o.url)
        let userId = param['userId']
        let currentPage = parseInt(param['currentPage'])
        let pageSize = parseInt(param['pageSize'])
        let dynamics = []
        for (let i = currentPage; i < pageSize + currentPage; i++) {
            let dynamic = {
                action: {name: '赞同', time: Mock.mock('@datetime')},
                post: {
                    id: Mock.mock('@guid'),
                    question: Mock.mock('@ctitle(20,40)'),
                    questionId: Mock.mock('@guid'),
                    content: {
                        text: Mock.mock('@cparagraph(6,20)'),
                        images: Mock.mock({
                            "images|0-2": [
                                Mock.mock('@image("200x100")')
                            ]
                        })['images'],
                        coverImage: Mock.mock('@image("200x100")'),
                    },
                    likeCount: Mock.mock('@integer(0,100)'),
                    dislikeCount: Mock.mock('@integer(0,100)'),
                    author: {
                        id: Mock.mock('@guid'),
                        name: Mock.mock('@cname'),
                        avatar: Mock.mock('@image("100x100")'),
                        desc: Mock.mock('@cparagraph(1,3)'),
                    },
                    publishTime: Mock.mock('@datetime'),
                    updateTime: Mock.mock('@datetime'),
                    commentNumber: Mock.mock('@integer(100,200)'),
                }
            }
            dynamics.push(dynamic)
        }
        return {
            code: 200,
            data: dynamics
        }

    })
    Mock.mock(/.*user\/info-detail.*/, o => {
        let param = getParams(o.url)
        let userId = param['id']
        return {
            code: 200,
            data: {
                id: userId,
                name: Mock.mock('@cname'),
                avatar: Mock.mock('@image("160x160")'),
                // 回答数
                postCount: Mock.mock('@integer(0,100)'),
                // 文章数
                articleCount: Mock.mock('@integer(0,100)'),
                // 被关注数
                followerCount: Mock.mock('@integer(0,100)'),
                // 提问数
                questionCount: Mock.mock('@integer(0,100)'),
                collectionCount: Mock.mock('@integer(0,100)'),
                followCount: Mock.mock('@integer(0,100)'),
                videoCount: Mock.mock('@integer(0,100)'),
                followedTopicCount: Mock.mock('@integer(0,100)'),
                followedQuestionCount: Mock.mock('@integer(0,100)'),
                // 个人简介
                industry: Mock.mock('@cword(2,4)'),
                gender: Mock.mock('@cword(1)'),
                location: Mock.mock('@cword(2,4)'),
                school: Mock.mock('@cword(2,4)'),
                jobHistory: Mock.mock('@cword(2,4)'),
                description: Mock.mock('@cword(2,4)'),
                resume: Mock.mock('@cparagraph(1,3)'),
                authentication: Mock.mock('@cword(2,4)'),
                backgroundImg: Mock.mock('@image("2000x400")'),
            }
        }
    })
    Mock.mock(/.*recommended-posts.*/, o => {
        let posts = []
        let pageSize = 5
        for (let i = 0; i < pageSize; i++) {
            let post = {
                id: Mock.mock('@guid'),
                question: Mock.mock('@ctitle(20,40)'),
                questionId: Mock.mock('@guid'),
                content: {
                    text: Mock.mock('@cparagraph(6,20)'),
                    images: Mock.mock({
                        "images|0-3": [
                            Mock.mock('@image("200x100")')
                        ]
                    })['images'],
                    coverImage: Mock.mock('@image("200x100")'),
                },
                likeCount: Mock.mock('@integer(0,100)'),
                dislikeCount: Mock.mock('@integer(0,100)'),
                author: {
                    id: Mock.mock('@guid'),
                    name: Mock.mock('@cname'),
                    avatar: Mock.mock('@image("100x100")'),
                    desc: Mock.mock('@cparagraph(1,3)'),
                },
                publishTime: Mock.mock('@datetime'),
                updateTime: Mock.mock('@datetime'),
                commentNumber: Mock.mock('@integer(100,200)'),
            }
            posts.push(post)
        }
        return posts
    })
    Mock.mock(/.*messages.*/, o => {
        let messages = []
        let param = getParams(o.url)
        let currentPage = parseInt(param['currentPage'])
        let pageSize = parseInt(param['pageSize'])
        for (let i = 0; i < 20; i++) {
            let msg = {
                content: `${Mock.mock('@cname')}给你发了一条消息`,
                id: Mock.mock('@guid')
            }
            messages.push(msg)
        }
        return messages
    })
    Mock.mock(/.*user\/info.*/, o => {
        let param = getParams(o.url)
        let userId = param['userId']
        return {
            code: 200,
            data: {
                id: userId,
                name: Mock.mock('@cname'),
                avatar: Mock.mock('@image("100x100")'),
                description: Mock.mock('@cparagraph(1,3)'),
                postCount: Mock.mock('@integer(0,100)'),
                articleCount: Mock.mock('@integer(0,100)'),
                followerCount: Mock.mock('@integer(0,100)'),
                followCount: Mock.mock('@integer(0,100)'),
                koinCount: Mock.mock('@integer(0,100)'),
                experienceCount: Mock.mock('@integer(0,100)'),

            }
        }
    })

    /**
     *
     * @type {*[]}
     * 后端
     * 1.根据帖子id查询根评论
     * 2.根据每个根评论id查询子评论（最多4个）
     *
     */

    Mock.mock(/.*comments.*/, o => {
        let param = getParams(o.url)
        // 转换为数值
        let currentPage = parseInt(param['currentPage'])
        let pageSize = parseInt(param['pageSize'])
        let postId = param['postId']
        let result = {
            code: 200,
            data: {
                comments: [],
                total: Mock.mock('@integer(100,200)')
            }
        }

        let comments = []
        // 根评论
        for (let i = currentPage; i < currentPage + pageSize; i++) {
            let comment = {
                // 这个评论的id
                id: Mock.mock('@guid'),

                name: Mock.mock('@cname'),
                content: Mock.mock('@cparagraph(1,3)'),
                // 帖子id:从属于哪个帖子
                postId,
                // 根评论id:从属于哪个根评论,如果是根评论则为和postId相同
                rootCommentId: postId,
                // 被回复评论的id，如果是根评论则为和postId相同，
                replyToId: postId,
                replyToName: Mock.mock('@cname'),
                likeCount: Mock.mock('@integer(0,300)'),
                publishTime: Mock.mock('@datetime'),
                // 发布者id
                publisherId: Mock.mock('@guid'),
                avatar: Mock.mock('@image("100x100")'),
                //对子回复的统计， 小于四时，后端直接查询子评论拼接到根评论，大于四时，需要打开dialog查看
                childCount: Mock.mock('@integer(0,5)'),
                isRootComment: true
            }
            comments.push(comment)
        }

        // 子评论
        let subComments = []
        comments.forEach(comment => {
            // 生成0到6个子评论
            // 随机数
            let random = Mock.mock('@integer(3,7)')
            for (let i = 0; i < random; i++) {
                let subComment = {
                    id: Mock.mock('@guid'),
                    content: Mock.mock('@cparagraph(1,3)'),
                    name: Mock.mock('@cname'),
                    // 帖子id:从属于哪个帖子
                    postId,
                    // 根评论id:从属于哪个根评论,如果是根评论则为和postId相同
                    rootCommentId: comment.id,
                    // 被回复评论的id，如果是根评论则为和postId相同，
                    replyToId: comment.id,
                    replyToName: Mock.mock('@cname'),
                    likeCount: Mock.mock('@integer(202,300)'),
                    publishTime: Mock.mock('@datetime'),
                    publisherId: Mock.mock('@guid'),
                    avatar: Mock.mock('@image("100x100")'),
                    isRootComment: false
                }
                subComments.push(subComment)
            }
        })
        comments = comments.concat(subComments)
        result.data.comments = comments
        return result
    })
    Mock.mock(/.*comments\/child-comments.*/, o => {
        let params = getParams(o.url)
        let id = params['id']
        let currentPage = parseInt(params['currentPage'])
        let pageSize = parseInt(params['pageSize'])
        let result = {
            code: 200,
            data: {
                comments: [],
                total: Mock.mock('@integer(100,200)')
            }
        }
        let comments = []
        for (let i = currentPage; i < currentPage + pageSize; i++) {
            let comment = {
                id: Mock.mock('@guid'),
                content: Mock.mock('@cparagraph(1,3)'),
                name: Mock.mock('@cname'),
                // 帖子id:从属于哪个帖子
                postId: Mock.mock('@guid'),
                // 根评论id:从属于哪个根评论,如果是根评论则为和postId相同
                rootCommentId: id,
                // 被回复评论的id，如果是根评论则为和postId相同，
                replyToId: Mock.mock('@guid'),
                replyToName: Mock.mock('@cname'),
                likeCount: Mock.mock('@integer(202,300)'),
                publishTime: Mock.mock('@datetime'),
                publisherId: Mock.mock('@guid'),
                avatar: Mock.mock('@image("100x100")'),
                isRootComment: false
            }
            comments.push(comment)
        }
        result.data.comments = comments
        return result
    })
    Mock.mock(/user\/post-card/, o => {
        let param = getParams(o.url)
        let userId = param['id']
        return {
            code: 200,
            data: {
                userInfo: {
                    id: userId,
                    name: Mock.mock('@cname'),
                    avatar: Mock.mock('@image("100x100")'),
                    description: Mock.mock('@cparagraph(1,3)'),
                    postCount: Mock.mock('@integer(0,100)'),
                    articleCount: Mock.mock('@integer(0,100)'),
                    followerCount: Mock.mock('@integer(0,100)'),
                },
            }
        }
    })
    Mock.mock(/.*user\/recommended-users.*/, o => {
        let param = getParams(o.url)
        let pageSize = parseInt(param['pageSize'])
        let result = {
            code: 200,
            data: {
                users: [],
                total: Mock.mock('@integer(100,200)')
            }
        }
        let users = []
        for (let i = 0; i < pageSize; i++) {
            let user = {
                id: Mock.mock('@guid'),
                name: Mock.mock('@cname'),
                avatar: Mock.mock('@image("100x100")'),
                reason: Mock.mock('@cparagraph(1,3)'),
            }
            users.push(user)
        }
        result.data.users = users
        return result
    })

}

export default mockSetUp
