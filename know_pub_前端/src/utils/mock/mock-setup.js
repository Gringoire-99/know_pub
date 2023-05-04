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
    Mock.mock(/.*recommended-posts.*/, o => {
        let posts = []
        let pageSize = 5
        for (let i = 0; i < pageSize; i++) {
            let post = {
                id: Mock.mock('@guid'),
                topic: Mock.mock('@ctitle(20,40)'),
                content: {
                    text: Mock.mock('@cparagraph(6,20)'),
                    images: Mock.mock({
                        "images|0-3": [
                            Mock.mock('@image("200x100")')
                        ]
                    })['images']
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
        let pageSize = param['pageSize']
        let pageIndex = param['pageIndex']
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
            name: Mock.mock('@cname'),
            avatar: '/src/assets/icon_small.png',
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
        let pageIndex = parseInt(param['pageIndex'])
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
        for (let i = pageIndex; i < pageIndex + pageSize; i++) {
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
                childCount: Mock.mock('@integer(0,5)'),
            }
            comments.push(comment)
        }

        // 子评论
        let subComments = []
        comments.forEach(comment => {
            // 生成0到6个子评论
            // 随机数
            let random = Mock.mock('@integer(0,5)')
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
                }
                subComments.push(subComment)
            }
        })
        comments = comments.concat(subComments)
        result.data.comments = comments
        return result
    })
}

export default mockSetUp
