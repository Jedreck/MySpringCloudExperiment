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













