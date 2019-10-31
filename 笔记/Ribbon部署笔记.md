Ribbon 负载均衡

BL（负载均衡）

集中式：偏硬件

进程内：偏软件，集成到消费端中（Ribbon）



### 消费端-前置配置试验

1.在consumer中添加依赖,其中eureka与ribbon整合

```xml
<!--Ribbon相关-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

2.修改yml，添加eureka信息

```yaml
spring:
  application:
    name: provider
    
eureka:
  #客户端注册进服务列表内
  client:
    register-with-eureka: false
    service-url:
      defaultZone: ${service.eureka02-url},${service.eureka02-url},${service.eureka03-url}
```

3.修改RestTemplate的配置，@Bean下添加@LoadBalanced

4.在启动类中添加@EnableEurekaClient

5.修改配置项中访问provider的ip，将ip从原来的 ip+port 修改为 大写的eureka中provider的名称



接下来完成通过微服务名字从eureka上找到provider并访问





### 正式配置

新建另外两个新的provider，注意修改port，name等配置，**spring.application.name、server.port.context-path要一样**，修改这三个provider同一个接口返回的信息，这样有助于分辨是哪个provider提供的数据。



此时，能够通过Ribbon和Eureka访问同一个consumer接口得到不同provider的数据

