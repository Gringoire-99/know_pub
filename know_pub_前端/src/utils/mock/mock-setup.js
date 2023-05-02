import Mock from "mockjs";

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
                likes: Mock.mock('@integer(0,100)'),
                dislikes: Mock.mock('@integer(0,100)'),
                author: {
                    id: Mock.mock('@guid'),
                    name: Mock.mock('@cname'),
                    avatar: Mock.mock('@image("100x100")'),
                    desc: Mock.mock('@cparagraph(1,3)'),
                },
                publish_time: Mock.mock('@datetime'),
                update_time: Mock.mock('@datetime'),
                comments: Mock.mock({
                    "comments|0-30": [
                        {
                            id: Mock.mock('@guid'),
                            content: Mock.mock('@cparagraph(1,3)'),
                            rootTopicId: Mock.mock('@guid'),
                            parentId: Mock.mock('@guid'),
                            likes: Mock.mock('@integer(0,100)'),
                            publish_time: Mock.mock('@datetime'),
                            avatar: Mock.mock('@image("100x100")'),
                        }]
                })['comments'],
            }
            posts.push(post)
        }
        return posts
    })
    Mock.mock(/.*messages.*/, o => {
        let messages = []
        let param = o.url.match(/.*pageSize=(\d+).*pageIndex=(\d+).*/)
        let pageSize = parseInt(param[1])
        let pageIndex = parseInt(param[2])
        for (let i = pageIndex; i < pageIndex + pageSize; i++) {
            let msg = {
                content: `${Mock.mock('@cname')}给你发了一条消息`,
                id: Mock.mock('@guid')
            }
            messages.push(msg)
        }
        return messages
    })
    Mock.mock(/.*user\/info.*/, o => {
        let param = o.url.match(/.*userId=(.+)&?/)
        let userId = param[1]
        return {
            name: Mock.mock('@cname'),
            avatar: '/src/assets/icon_small.png',
        }
    })
}

export default mockSetUp
