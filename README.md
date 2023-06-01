# KnowPub (一个类似知乎的知识分享论坛)（vue+springboot）

**API接口文档👇**  
**https://apifox.com/apidoc/shared-d052ea40-29dd-4b9b-893b-a33564238163**


现在支持图片上传啦~  在nacos的config里配置oss.yaml为自己的阿里云RAM就可以用了♥
![image](https://github.com/Gringoire-99/know_pub/blob/main/know_pub_%E5%89%8D%E7%AB%AF/src/assets/aow.jpg)

改成分布式了，要打开nacos,不然启动不了

   
   # ✨TODO✨:
   1. login
   5. comment 
   5. post
   3. userInfo
   4. question 
   6. tag 
   7. upload
   8. article  <- now
   9. etc...
   
   后台管理系统
   1. todo...
   
# 👌QuickStart👌

### 启动前端
1. 安装node.js(推荐版本18)
2. npm install
3. npm run dev

### 启动后端
1. 安装nacos，以单点模式启动
2. (可选)访问nacos控制台(默认账号密码:nacos)，在配置管理里添加oss.yaml的配置(参考阿里云的文档)
3. ide和yaml里配置好数据源(know_pub)
4. 运行kp_common模块下的createsql
5. 启动
