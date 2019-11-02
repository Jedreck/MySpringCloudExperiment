## Hytrix的一些概念

一个熔断机制

主要附着于客户端，与服务端无关

## 部署

### hystrix服务端

- 首先新建一个provider，仿照8001。

  此时，hytrix与服务端是紧密联系的，未解耦。

- 在pom文件中，添加

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

- 在一个controller的一个方法中，修改成

```java
@GetMapping("/getAll")
@HystrixCommand(fallbackMethod = "getAllFallback")
    public List getAll() {
    return studentService.getAll();
}

public List getAllFallback() {
    ArrayList arrayList = new ArrayList();
    arrayList.add("{\"Hystri8011\":\"服务暂停，稍后再试\"}");
    return arrayList;
}
```
注意：入参和返回要相同


- 修改启动类，添加@EnableCircuitBreaker

此时，启动eureka，consumer80和hytrix服务端，能够从服务端访问到数据



### 客户端

接下来进行解耦，让熔断与服务端无关

- 在客户端使用的API中新建一个工厂类来提供错误的返回值，继承FallbackFactory，添加需要代理的泛型

```java
package com.jedreck.serviceapi.service;

@Component
public class StudentClientServiceFallbackFactory implements FallbackFactory<StudentClientService> {
    @Override
    public StudentClientService create(Throwable throwable) {
        return new StudentClientService() {
            @Override
            public List getAll() {
                ArrayList<HashMap> arrayList = new ArrayList<>();
                HashMap<String,String> map = new HashMap<>(1);
                map.put("接口","服务暂停，稍后再试。。。");
                arrayList.add(map);
                return arrayList;
            }
        };
    }
}
```



- 在被代理的接口中，添加

```java
@FeignClient(value = "${provider01.name}",fallbackFactory = StudentClientServiceFallbackFactory.class)
```



- feign的consumer中yml添加

```yaml
feign:
  hystrix:
    #启动hystrix
    enabled: true
```

此时启动8001，eureka，feign的consumer，访问成功，关闭8001后，有提示



## Hystrix Dashboard 搭建

新建一个maven工程

pom：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
</dependencies>
```

yml:

```yaml
server:
  port: 9001
```

正常的启动类上添加：@EnableHystrixDashboard

### 其他工程

在有Hytrix的服务端中，添加

```xml
<!--info网页信息/dashboard-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

在yml中添加

```yaml
management:
    endpoints:
        web:
            exposure:
            	include: hystrix.stream
```

此时启动eureka、dashboard和有hystrix的服务端，能够得到数据……

 http://127.0.0.1:9001/hystrix 