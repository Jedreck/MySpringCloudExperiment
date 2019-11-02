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

 http://127.0.0.1.100:9527/zuul/provider/provider/student/getAll 





### 增强配置

yml:

```yaml
#zuul配置
zuul:
  routes:
    #这个可以将provider的路径，转移到/pro上，隐藏真实信息
    provide:
      serviceId: provider
      path: /pro/**
```

此时可以同时使用两种路径进行访问

 http://127.0.0.1:9527/zuul/pro/provider/student/getAll 

 http://192.168.1.100:9527/zuul/provider/provider/student/getAll 

yml：

```yaml
#zuul配置
zuul:
  routes:
    #这个可以将provider的路径，转移到/pro上，隐藏真实信息
    provide:
      serviceId: provider
      path: /pro/**
  #列出的服务将不能使用原url进行访问，或使用 "*"
  #ignored-services: provider
  ignored-services: "*"
```

此时，只能用被代理之后的路径进行访问

 http://192.168.1.100:9527/zuul**/pro**/provider/student/getAll 

```yaml
#zuul配置
zuul:
  routes:
    #这个可以将provider的路径，转移到/pro上，隐藏真实信息
    provide:
      serviceId: provider
      path: /pro/**
  #列出的服务将不能使用原url进行访问，或使用 "*"
  #ignored-services: provider
  ignored-services: "*"
  #同意前缀
  prefix: /jedreck
```

添加统一前缀，需要在服务名前加上前缀

 http://127.0.0.1:9527/zuul**/jedreck**/pro/provider/student/getAll 