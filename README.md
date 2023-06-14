# KnowPub

**API接口文档👇**  
**https://apifox.com/apidoc/shared-d052ea40-29dd-4b9b-893b-a33564238163**

重构中...
   
   # ✨TODO✨:
   1. login
   2. comment 
   3. post
   4. userInfo
   5. question 
   6. tag 
   7. upload
   8. article  <- now
   9. search
   10. collection
   
   后台管理系统
   1. todo...
   
# 👌QuickStart👌

### 启动前端
1. 安装node.js(推荐版本18)
2. npm install
3. npm run dev

### 启动后端
1. 安装nacos，以单点模式启动
2. (可选)访问nacos控制台(默认账号密码:nacos)，在配置管理里添加oss.yaml的配置(参考阿里云的文档)，如果此项没有配置，kp_other,和图片上传功能不可使用
3. ide和yaml里配置好数据源(know_pub)
4. 运行kp_common模块下的createsql
5. 打开redis服务
6. 启动
