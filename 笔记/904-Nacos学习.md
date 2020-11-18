### 安装

#### docker

使用官方docker镜像进行修改mysql参数，使用已有mysql服务，参考 `https://github.com/nacos-group/nacos-docker/blob/master/README_ZH.md` 。

其中需要预先执行sql脚本 `https://github.com/alibaba/nacos/blob/master/config/src/main/resources/META-INF/nacos-db.sql`

默认账号/密码：nacos


### 运行

nacos配置必须使用 `bootstrap.yml` ，它比 `application.yml` 优先级高。
