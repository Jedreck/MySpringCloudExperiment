## 路由与网关

### 部署

新建一个maven项目

pom:

```xml
<dependencies>
    <dependency>
        <groupId>com.jedreck</groupId>
        <artifactId>service-api</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>


    <!--info网页信息/dashboard-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

yml:

```yaml
server:
  port: 9527
  servlet:
    context-path: /zuul
spring:
  application:
    name: zuul-9527
eureka:
  #客户端注册进服务列表内
  client:
    instance:
      instance-id: zuul-9527
      #访问路径可以显示IP地址
      prefer-ip-address: true
    service-url:
      defaultZone: ${service.eureka02-url},${service.eureka02-url},${service.eureka03-url}
info:
  app.name: springcloud-experiment
  company.name: com.jedreck
  build.artifactId: @project.artifactId@
  build.version: @project.version@

#自定义信息
service:
  eureka01-url: http://eureka7001:7001/eureka/
  eureka02-url: http://eureka7002:7002/eureka/
  eureka03-url: http://eureka7003:7003/eureka/
```

host中添加

```java
127.0.0.1 zuul9527
```

启动注解:

```java
@EnableZuulProxy
```

启动eureka，8001的provider，zuul则可以通过路由访问到信息

 http://192.168.1.100:9527/zuul/provider/provider/student/getAll 





### 增强配置

