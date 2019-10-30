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

### 优化

#### info信息

1.在eureka界面中，已注册的客户端有一个连接能够点进去了解情况，可以在client的yml中添加下面的配置信息完善页面上的信息

```yaml
eureka:
  instance:
    #在注册处连接显示的名称
    instance-id: eurek-aname-provider-8001
    #访问路径可以显示IP地址
    prefer-ip-address: true
```

2.点击进连接为404，

1.需要在client项目中添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

在yml中添加(YML需要使用@来获取pom中的内容)

```yaml
info:
  app.name: atguigu-microservicecloud
  company.name: www.atguigu.com
  build.artifactId: @project.artifactId@
  build.version: @project.version@
```

2.在总的pom文件中添加

```xml
<!--info信息-->
<finalName>springcloud-experiment</finalName>
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
    </resource>
</resources>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-resources-plugin</artifactId>
        <configuration>
            <delimiters>
                <delimit>$</delimit>
            </delimiters>
        </configuration>
    </plugin></plugins>
```

#### 自我保护

默认开启，关闭可在eureka的yml中添加

```yml
server:
  #自我保护,false-关闭
  enable-self-preservation: false
```

## 服务发现

在client的Controller中自动实例化DiscoveryClient

```java
@Autowired
    private DiscoveryClient discoveryClient;
```

然后使用

```java
discoveryClient.getServices();
//和
discoveryClient.getInstances(/*大写的Client名称*/)
```

