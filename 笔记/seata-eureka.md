### 有关seata的配置

#### server端：

配置 `file.conf` 和 `registry.conf` ，

 `registry.conf` 中，`registry` 修改 `type = "eureka"`，使用 eureka 进行注册；

 `file.conf` 中，`store` 修改 `mode` ，并修改对应的配置；

### client端：

- 首先要由 `file.conf` 和 `registry.conf` 配置，要修改响应地方，参考 server 端，pom引用

  ```xml
  <!--seata-->
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-alibaba-seata</artifactId>
      <version>2.1.0.RELEASE</version>
      <exclusions>
          <exclusion>
              <artifactId>seata-all</artifactId>
              <groupId>io.seata</groupId>
          </exclusion>
      </exclusions>
  </dependency>
  <dependency>
      <groupId>io.seata</groupId>
      <artifactId>seata-all</artifactId>
      <version>1.3.0</version>
  </dependency>
  ```

  

- 其次，`application.yml` 中添加配置

```yaml
spring:
  cloud:
    alibaba:
      seata:
        # 这里要和 transaction service group mapping 的一致，并且与service端的也一致
        tx-service-group: my_test_tx_group
```

- 在应该添加事务的入口增加

```java
@GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class);
```

- 数据库连接使用自定义代理

```java
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSource(DataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy)throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
```

- 在数据库添加数据表----使用AT模式(默认)时为必须

  ```sql
  CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    `ext` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
  ```

  

