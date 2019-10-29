# 单点

## Eureka项目
### eureka项目

1.新建一个maven的eureka项目

2.导入包（用netflix的包，会让客户端无法注册近服务中）

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```

3.添加配置项

```properties
eureka:  
  instance:
    # eureka服务端实例名称
    hostname: eureka01  
  client:    
    #不向注册中心注册自己
    register-with-eureka: false
    #自己端就是注册中心，职责是维护服务实例，不是检索服务
    fetch-registry: false
    service-url:
    #设置eureka server的交互地址查询服务和注册服务都需要依赖这个地址
      defaultZone: http://127.0.0.1:${server.port}/eureka/
```



4.在application启动文件中，添加

```java
@EnableEurekaServer
```
此时，能够启动服务，启动后，访问 ip:port 能够进入eureka界面。

### client项目

1.从一个已有的项目中添加依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```

2.在application启动文件中，添加

```java
@EnableEurekaClient
```

此时先启动eureka项目，再启动client项目，能够在eureka界面中看到client项目的信息。