# 不使用外键
CREATE DATABASE IF NOT EXISTS know_pub CHAR SET utf8;
USE know_pub;
CREATE TABLE IF NOT EXISTS know_pub.user
(
    id               char(40)     NOT NULL primary key comment '用户id',
    email            char(40) UNIQUE comment '邮箱',
    phone            char(40) UNIQUE comment '手机号',
    name             char(40)     NOT NULL comment '用户名',
    password         varchar(200) NOT NULL comment '密码',
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
    id               char(40)     NOT NULL primary key comment '评论id',
    content          varchar(256) NOT NULL comment '评论内容',
    name             char(40)     NOT NULL comment '评论人的姓名',
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
    id            char(40)       NOT NULL primary key comment '博文id',
    question      varchar(256)   NOT NULL comment '问题/标题',
    question_id   char(40)       NOT NULL comment '问题id',
    content       varchar(10000) NOT NULL comment '内容(富文本)',
    cover         varchar(100)   default '' comment '封面',
    images        varchar(10000) default '' comment '图片，使用逗号分隔',
    like_count    int            default 0 comment '点赞数',
    dislike_count int            default 0 comment '踩数',
    author_id     char(40)       NOT NULL comment '作者id,需要根据id去user表中查找基础信息',
    publish_time  datetime       default now() comment '发布时间',
    update_time   datetime       default now() comment '更新时间',
    status        int            default 0 comment '状态,0:正常,1:禁用',
    comment_count int            default 0 comment '评论数'

);
CREATE TABLE IF NOT EXISTS question
(
    id               char(40)       NOT NULL primary key comment '问题id',
    question         varchar(256)   NOT NULL comment '问题/标题',
    content          varchar(10000) NOT NULL comment '内容(富文本)',
    cover            varchar(100)   default '' comment '封面',
    images           varchar(10000) default '' comment '图片，使用逗号分隔',
    like_count       int            default 0 comment '点赞数',
    author_id        char(40)       NOT NULL comment '作者id,需要根据id去user表中查找基础信息',
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
    name             char(40) NOT NULL PRIMARY KEY comment '标签名',
    description      varchar(256) default '' comment '标签描述',
    create_time      datetime     default now() comment '创建时间',
    status           int          default 0 comment '状态,0:正常,1:禁用',
    view_count       int          default 0 comment '浏览数',
    question_count   int          default 0 comment '问题数',
    discuss_count    int          default 0 comment '讨论数',
    collection_count int          default 0 comment '收藏数'
);


/*Table structure for table `sys_menu` */


CREATE TABLE IF NOT EXISTS `sys_menu`
(
    `id`          char(40)    NOT NULL,
    `menu_name`   varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
    `path`        varchar(200)         DEFAULT NULL COMMENT '路由地址',
    `component`   varchar(255)         DEFAULT NULL COMMENT '组件路径',
    `visible`     char(1)              DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1)              DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100)         DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100)         DEFAULT '#' COMMENT '菜单图标',
    `create_by`   bigint(20)           DEFAULT NULL,
    `create_time` datetime             DEFAULT NULL,
    `update_by`   bigint(20)           DEFAULT NULL,
    `update_time` datetime             DEFAULT NULL,
    `del_flag`    int(11)              DEFAULT '0' COMMENT '是否删除（0未删除 1已删除）',
    `remark`      varchar(500)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

/*Table structure for table `sys_role` */


CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`          char(40) NOT NULL,
    `name`        varchar(128) DEFAULT NULL,
    `role_key`    varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
    `status`      char(1)      DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `del_flag`    int(1)       DEFAULT '0' COMMENT 'del_flag',
    `create_by`   bigint(200)  DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_by`   bigint(200)  DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

/*Table structure for table `sys_role_menu` */


CREATE TABLE IF NOT EXISTS `sys_role_menu`
(
    `role_id` char(40) NOT NULL COMMENT '角色ID',
    `menu_id` char(40) NOT NULL DEFAULT '0' COMMENT '菜单id',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*Table structure for table `sys_user` */


CREATE TABLE IF NOT EXISTS `sys_user_role`
(
    `user_id` char(40)          DEFAULT 1 COMMENT '用户id,1',
    `role_id` char(40) NOT NULL DEFAULT '1' COMMENT '角色id,1:User,0:admin',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
# 设定权限
INSERT INTO sys_menu(id, menu_name, perms)
VALUES ('1', '查看用户详情', 'user:info-detail');

# 设定角色
INSERT INTO sys_role(id, name, role_key)
VALUES ('1', 'User', 'user'),
       ('0', 'admin', 'admin');
# 设定角色拥有的权限
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES ('1', '1'),
       ('0', '1');

# 设定用户拥有的角色
# INSERT INTO sys_user_role(user_id, role_id)
# VALUES ('1', '1'),
#        ('0', '0');
