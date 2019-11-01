## Feign

能够适应，

通过微服务名字获得调用地址

或者通过 接口+注解 来获得调用的服务

**创建一个接口，通过接口调用**即可



### 部署

##### 修改API工程

添加一个service包，添加一个接口，与provider端的接口相同

添加一个注解，value填入Eureka中Provider的名字

**这里FeignClient只是获取了ip和端口，没有项目网址，必须加上**

```java
@FeignClient(value = "PROVIDER")
public interface StudentClientService {
    @GetMapping(value = "provider/student/getAll")
    List<Student> getAll();
}
```



##### 新建Feign工程

新建一个consumer的Feign工程

- 参考之前的consumer，去掉Ribbon的相关引用和类，但是pom保留依赖



- 重写Controller，自动注入API中的service，然后像写普通Controller一样访问数据

  ```java
  @RestController
  @RequestMapping("/student")
  public class StudentController {
      @Autowired
      private StudentClientService service;
  
      @GetMapping("/getAll")
      public List getAll() {
          return service.getAll();
      }
  }
  ```



- 修改启动类，添加下面的注解，注意：引用的包是兼容两个工程的包
```java
@EnableFeignClients(basePackages = {"com.jedreck"})
@ComponentScan("com.jedreck")
```

