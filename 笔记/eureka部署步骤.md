# 单点

## Eureka项目
### eureka项目

1.新建一个maven的eureka项目

2.导入包（用netflix的包，会让客户端无法注册近服务中）

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
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
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
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

能够发现已注册到eureka中的client信息

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



# 多点

多点eureka服务

1.新建多个eureka项目，新建过程与单点相同。

2.配置项

若在defaultZone中添加的是ip，则在eureka网页中DS Replicas只有一个，因为这里的ip相同。避免只出现一个，修改host，添加

```json
#学习springcloud主机名
127.0.0.1 eureka7001
127.0.0.1 eureka7002
127.0.0.1 eureka7003
```

配置项：

```yaml
server:
  port: 700Xxx
spring:
  application:
    name: eureka-700Xxx

eureka:
  instance:
    # eureka服务端实例名称
    hostname: eureka02
  client:
    #不向注册中心注册自己
    register-with-eureka: false
    #自己端就是注册中心，职责是维护服务实例，不是检索服务
    fetch-registry: false
    service-url:
      #设置eureka server的交互地址查询服务和注册服务都需要依赖这个地址
      defaultZone: ${service.eureka01-url},${service.eureka03-url}

#自定义信息
service:
  eureka01-url: http://eureka7001:7001/eureka/
  eureka02-url: http://eureka7002:7002/eureka/
  eureka03-url: http://eureka7003:7003/eureka/
```

三个都启动后，能够看到互相注册到eureka中



## 记事

ACID （Atomicity 原子性；Consistency 一致性；Isolation 独立性；Durability 持久性）

与

CAP （Consistency 强一致性；Avaibility 可用性；Partition Tolerance 分区容错性）

分布式系统都是CAP，且只能三选二，

zookeeper属于CP，

eureka属于AP，各个结点是平等的

**（关注zookeeper与eureka的区别）**

