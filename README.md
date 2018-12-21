**项目说明** 

- quickIt是一个快速开发的应用后台管理系统

采用流行的框架springBoot+mybatis+elasticsearch+shiro+redis+activiti+fastdfs开发


项目主要功能：

权限管理、工作流审批、通知、定时任务、文件上传、代码生成器



 **技术选型：**
  
- 核心框架：Spring Boot 2.1.0
- 工作流引擎：Activiti 5.22.0
- 缓存：redis 3.07
- 权限框架：Apache Shiro 1.4
- 持久层框架：MyBatis 3.3
- 数据库：mysql 5.7
- 定时器：Quartz 2.2.3
- 前端页面：Vue2.x、jstl、bootstrap、layer、layerUI、freemarker、jsp


 **软件环境** 
- JDK1.8
- MySQL5.7.17
- Maven3.0
- Tomcat8.0
- redis 3.07
- Fastdfs5.08
- Elasticsearch6.5

 **本地部署**
- 创建数据库quickit，数据库编码为UTF-8,项目sql/db/quickit.sql脚本
- 修改application-dev.yml文件，更改MySQL账号和密码
- 启动redis服务,可在官方群下载。
- 启动Fastdfs服务,可在官方群下载。
- 启动Elasticsearch服务,可在官方群下载。
- 本项目启动方式为外置Tomcat启动
- 项目访问路径：http://localhost:8080/quickit/






