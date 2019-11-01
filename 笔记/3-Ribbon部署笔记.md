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



6.新建另外两个新的provider，注意修改port，name等配置，**spring.application.name、server.port.context-path要一样**，修改这三个provider同一个接口返回的信息，这样有助于分辨是哪个provider提供的数据。



此时，能够通过Ribbon和Eureka访问同一个consumer接口得到不同provider的数据

### 配置-IRule

1.在Consumer中的Config中添加一个Bean，选择负载均衡的策略，

```java
@Bean
    public IRule myRule() {
        //Ribbon 使用默认的轮询
//        return new RoundRobinRule();
        //Ribbon 使用随机替代默认的轮询
        return new RandomRule();
    }
```

----------------------------------------------------------------------------------------------------------

Ribbon中有多种负载均衡的策略

- IRule
  这是所有负载均衡策略的父接口，里边的核心方法就是choose方法，用来选择一个服务实例。

- AbstractLoadBalancerRule
  AbstractLoadBalancerRule是一个抽象类，里边主要定义了一个ILoadBalancer，就是我们上文所说的负载均衡器，负载均衡器的功能我们在上文已经说的很详细了，这里就不再赘述，这里定义它的目的主要是辅助负责均衡策略选取合适的服务端实例。

- RandomRule
  看名字就知道，这种负载均衡策略就是随机选择一个服务实例，看源码我们知道，在RandomRule的无参构造方法中初始化了一个Random对象，然后在它重写的choose方法又调用了choose(ILoadBalancer lb, Object key)这个重载的choose方法，在这个重载的choose方法中，每次利用random对象生成一个不大于服务实例总数的随机数，并将该数作为下标所以获取一个服务实例。

- RoundRobinRule
  RoundRobinRule这种负载均衡策略叫做线性负载均衡策略，也就是我们在上文所说的BaseLoadBalancer负载均衡器中默认采用的负载均衡策略。这个类的choose(ILoadBalancer lb, Object key)函数整体逻辑是这样的：开启一个计数器count，在while循环中遍历服务清单，获取清单之前先通过incrementAndGetModulo方法获取一个下标，这个下标是一个不断自增长的数先加1然后和服务清单总数取模之后获取到的（所以这个下标从来不会越界），拿着下标再去服务清单列表中取服务，每次循环计数器都会加1，如果连续10次都没有取到服务，则会报一个警告No available alive servers after 10 tries from load balancer: XXXX。

- RetryRule
  看名字就知道这种负载均衡策略带有重试功能。首先RetryRule中又定义了一个subRule，它的实现类是RoundRobinRule，然后在RetryRule的choose(ILoadBalancer lb, Object key)方法中，每次还是采用RoundRobinRule中的choose规则来选择一个服务实例，如果选到的实例正常就返回，如果选择的服务实例为null或者已经失效，则在失效时间deadline之前不断的进行重试（重试时获取服务的策略还是RoundRobinRule中定义的策略），如果超过了deadline还是没取到则会返回一个null。

- WeightedResponseTimeRule
  WeightedResponseTimeRule是RoundRobinRule的一个子类，在WeightedResponseTimeRule中对RoundRobinRule的功能进行了扩展，WeightedResponseTimeRule中会根据每一个实例的运行情况来给计算出该实例的一个权重，然后在挑选实例的时候则根据权重进行挑选，这样能够实现更优的实例调用。WeightedResponseTimeRule中有一个名叫DynamicServerWeightTask的定时任务，默认情况下每隔30秒会计算一次各个服务实例的权重，权重的计算规则也很简单，如果一个服务的平均响应时间越短则权重越大，那么该服务实例被选中执行任务的概率也就越大。

- ClientConfigEnabledRoundRobinRule
  ClientConfigEnabledRoundRobinRule选择策略的实现很简单，内部定义了RoundRobinRule，choose方法还是采用了RoundRobinRule的choose方法，所以它的选择策略和RoundRobinRule的选择策略一致，不赘述。

- BestAvailableRule
  BestAvailableRule继承自ClientConfigEnabledRoundRobinRule，它在ClientConfigEnabledRoundRobinRule的基础上主要增加了根据loadBalancerStats中保存的服务实例的状态信息来过滤掉失效的服务实例的功能，然后顺便找出并发请求最小的服务实例来使用。然而loadBalancerStats有可能为null，如果loadBalancerStats为null，则BestAvailableRule将采用它的父类即ClientConfigEnabledRoundRobinRule的服务选取策略（线性轮询）。

- PredicateBasedRule
  PredicateBasedRule是ClientConfigEnabledRoundRobinRule的一个子类，它先通过内部定义的一个过滤器过滤出一部分服务实例清单，然后再采用线性轮询的方式从过滤出来的结果中选取一个服务实例。

- ZoneAvoidanceRule
  ZoneAvoidanceRule是PredicateBasedRule的一个实现类，只不过这里多一个过滤条件，ZoneAvoidanceRule中的过滤条件是以ZoneAvoidancePredicate为主过滤条件和以AvailabilityPredicate为次过滤条件组成的一个叫做CompositePredicate的组合过滤条件，过滤成功之后，继续采用线性轮询的方式从过滤结果中选择一个出来。



##### Ribbon自带负载均衡策略比较

| 策略名                    | 策略声明                                                     | 策略描述                                                     | 实现说明                                                     |
| ------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| BestAvailableRule         | public class BestAvailableRule extends ClientConfigEnabledRoundRobinRule | 选择一个最小的并发请求的server                               | 逐个考察Server，如果Server被tripped了，则忽略，在选择其中ActiveRequestsCount最小的server |
| AvailabilityFilteringRule | public class AvailabilityFilteringRule extends PredicateBasedRule | 过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值） | 使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个server的运行状态 |
| WeightedResponseTimeRule  | public class WeightedResponseTimeRule extends RoundRobinRule | 根据相应时间分配一个weight，相应时间越长，weight越小，被选中的可能性越低。 | 一个后台线程定期的从status里面读取评价响应时间，为每个server计算一个weight。Weight的计算也比较简单responsetime 减去每个server自己平均的responsetime是server的权重。当刚开始运行，没有形成statas时，使用roubine策略选择server。 |
| RetryRule                 | public class RetryRule extends AbstractLoadBalancerRule      | 对选定的负载均衡策略机上重试机制。                           | 在一个配置时间段内当选择server不成功，则一直尝试使用subRule的方式选择一个可用的server |
| RoundRobinRule            | public class RoundRobinRule extends AbstractLoadBalancerRule | roundRobin方式轮询选择server                                 | 轮询index，选择index对应位置的server                         |
| RandomRule                | public class RandomRule extends AbstractLoadBalancerRule     | 随机选择一个server                                           | 在index上随机，选择index对应位置的server                     |
| ZoneAvoidanceRule         | public class ZoneAvoidanceRule extends PredicateBasedRule    | 复合判断server所在区域的性能和server的可用性选择server       | 使用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server，前一个判断判定一个zone的运行性能是否可用，剔除不可用的zone（的所有server），AvailabilityPredicate用于过滤掉连接数过多的Server。 |



----------------------------------------------------------------------------------------------------------

#### 自定义使用的策略

在consumer中

自定义的策略类不能再@ComponentScan扫描的包和子包下

在启动类上一层包中，新建一个包myrule，新建一个MySelfRule类，作为configration

```java
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        return new BestAvailableRule();
    }
}
```

在启动类中添加

```java
//启动服务的时候使用自己的配置
@RibbonClient(name = "${provider01.name}",configuration = MySelfRule.class)
```

此时全部启动，能够根据自己选择的Rule来确定策略

#### 自定义IRule

自己写一个类，参照Round策略的写法

```java
FirstRule extends AbstractLoadBalancerRule
```

