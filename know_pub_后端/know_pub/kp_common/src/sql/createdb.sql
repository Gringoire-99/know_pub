# 不使用外键
CREATE DATABASE IF NOT EXISTS know_pub CHAR SET utf8;
USE know_pub;
CREATE TABLE IF NOT EXISTS know_pub.user
(
    id               char(40)    NOT NULL primary key comment '用户id',
    email            char(40) UNIQUE comment '邮箱',
    phone            char(40) UNIQUE comment '手机号',
    name             char(20)    NOT NULL comment '用户名',
    password         varchar(20) NOT NULL comment '密码',
    avatar           varchar(100) default 'http://dummyimage.com/100x100' comment '头像',
    description      varchar(256) default '这个人很懒，什么都没有留下' comment '描述',
    create_time      datetime     default now() comment '创建时间',
    post_count       int          default 0 comment '发表回答数',
    comment_count    int          default 0 comment '评论数',
    like_count       int          default 0 comment '点赞数',
    follow_count     int          default 0 comment '关注数',
    follower_count   int          default 0 comment '粉丝数',
    question_count   int          default 0 comment '提问数',
    answer_count     int          default 0 comment '回答数',
    collection_count int          default 0 comment '收藏数',
    video_count      int          default 0 comment '视频数',
    article_count    int          default 0 comment '文章数',
    status           int          default 0 comment '状态,0:正常,1:禁用',
    koin_count       int          default 0 comment 'koin货币数',
    experience       int          default 0 comment '经验值',
    industry         varchar(20)  default '' comment '行业',
    company          varchar(20)  default '' comment '公司',
    job              varchar(20)  default '' comment '职位',
    school           varchar(20)  default '' comment '学校',
    location         varchar(20)  default '' comment '地区',
    gender           varchar(10)  default '' comment '性别',
    resume           varchar(100) default '' comment '简单介绍',
    auth             varchar(20)  default 0 comment '个人认证',
    background       varchar(100) default 'http://dummyimage.com/2000x1000' comment '背景图'
);
CREATE TABLE IF NOT EXISTS comment
(
    id               char(30)     NOT NULL primary key comment '评论id',
    content          varchar(256) NOT NULL comment '评论内容',
    name             char(20)     NOT NULL comment '评论人的姓名',
    avatar           varchar(100) default 'http://dummyimage.com/100x100' comment '评论人的头像',
    create_time      datetime     default now() comment '评论时间',
    like_count       int          default 0 comment '点赞数',
    status           int          default 0 comment '状态,0:正常,1:禁用',
    post_id          char(30)     NOT NULL comment '评论的文章id',
    user_id          char(30)     NOT NULL comment '评论人的id',
    reply_to_name    char(20)     default '' comment '回复的评论人的姓名',
    reply_to_user_id char(30)     default '' comment '回复的评论人的id',
    is_root_comment  int          default 1 comment '是否是根评论,1:是,0:不是',
    child_count      int          default 0 comment '子评论数,当数量小于等于4时查出所有子评论,大于4时不查'
);
CREATE TABLE IF NOT EXISTS post
(
    id            char(30)       NOT NULL primary key comment '博文id',
    question      varchar(256)   NOT NULL comment '问题/标题',
    question_id   char(30)       NOT NULL comment '问题id',
    content       varchar(10000) NOT NULL comment '内容(富文本)',
    cover         varchar(100)   default '' comment '封面',
    images        varchar(10000) default '' comment '图片，使用逗号分隔',
    like_count    int            default 0 comment '点赞数',
    dislike_count int            default 0 comment '踩数',
    author_id     char(30)       NOT NULL comment '作者id,需要根据id去user表中查找基础信息',
    publish_time  datetime       default now() comment '发布时间',
    update_time   datetime       default now() comment '更新时间',
    status        int            default 0 comment '状态,0:正常,1:禁用',
    comment_count int            default 0 comment '评论数'

);
CREATE TABLE IF NOT EXISTS question
(
    id               char(30)       NOT NULL primary key comment '问题id',
    question         varchar(256)   NOT NULL comment '问题/标题',
    content          varchar(10000) NOT NULL comment '内容(富文本)',
    cover            varchar(100)   default '' comment '封面',
    images           varchar(10000) default '' comment '图片，使用逗号分隔',
    like_count       int            default 0 comment '点赞数',
    author_id        char(30)       NOT NULL comment '作者id,需要根据id去user表中查找基础信息',
    publish_time     datetime       default now() comment '发布时间',
    update_time      datetime       default now() comment '更新时间',
    status           int            default 0 comment '状态,0:正常,1:禁用',
    comment_count    int            default 0 comment '评论数',
    answer_count     int            default 0 comment '回答数',
    collection_count int            default 0 comment '收藏数',
    view_count       int            default 0 comment '浏览数',
    tag_names        varchar(50)    default '' comment '标签名，使用逗号分隔'
);
CREATE TABLE IF NOT EXISTS tag
(
    name             varchar(20) NOT NULL PRIMARY KEY comment '标签名',
    description      varchar(256) default '' comment '标签描述',
    create_time      datetime     default now() comment '创建时间',
    status           int          default 0 comment '状态,0:正常,1:禁用',
    view_count       int          default 0 comment '浏览数',
    question_count   int          default 0 comment '问题数',
    discuss_count    int          default 0 comment '讨论数',
    collection_count int          default 0 comment '收藏数'
);