package com.jedreck.tester2.service;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.jedreck.tester2.dao.PersonDao;
import com.jedreck.tester2.entitys.PersonEntity;
import com.jedreck.tester2.feign.Tester2Test1Service;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * AT 模式和 Spring @Transactional 注解连用时需要注意什么 ？
 * A: @Transactional 可与 DataSourceTransactionManager 和 JTATransactionManager 连用分别表示本地事务和XA分布式事务，大家常用的是与本地事务结合。
 * 当与本地事务结合时，@Transactional和@GlobalTransaction连用，@Transactional 只能位于标注在@GlobalTransaction的同一方法层次或者位于@GlobalTransaction 标注方法的内层。
 * 这里分布式事务的概念要大于本地事务，若将 @Transactional 标注在外层会导致分布式事务空提交，当@Transactional 对应的 connection 提交时会报全局事务正在提交或者全局事务的xid不存在。
 */
@Service
public class Test1Service {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private Tester2Test1Service tester2Test1Service;

    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    @GlobalLock
    @Transactional
    public boolean step1() {
        // 修改数据库
        PersonEntity personEntity = new PersonEntity();
        long id = 2L;
        personEntity.setId(id);
        int i = RandomUtil.randomInt(50, 200);
        personEntity.setHeight(i);
        personDao.updateByPrimaryKeySelective(personEntity);

        PersonEntity person = personDao.getById(id);
        System.out.println(111);
        System.out.println(JSON.toJSONString(person));

        // 调用其他服务修改数据库
        tester2Test1Service.changeName(personEntity);

        person = personDao.selectByPrimaryKey(id);
        System.out.println(222);
        System.out.println(JSON.toJSONString(person));

        return true;
    }

    //    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    @Transactional
    public Boolean step2() {
        // 修改数据库
        PersonEntity personEntity = new PersonEntity();
        int i = RandomUtil.randomInt(50, 200);
        personEntity.setId(3L);
        personEntity.setHeight(i);
        personEntity.setName(RandomUtil.randomNumbers(4));
        personDao.updateByPrimaryKeySelective(personEntity);

        System.out.println(i);

        // 调用其他服务修改数据库
        tester2Test1Service.insertPerson(personEntity);
        return true;
    }

    /**
     * 配合step1
     * 测试是否脏读
     */
    public boolean step3() {
//        PersonEntity person = personDao.getById(2L);
//        System.out.println(555);
//        System.out.println(JSON.toJSONString(person));

        Example example = new Example(PersonEntity.class);
        example.setForUpdate(true);
        example.createCriteria().andEqualTo("id", 2L);
        PersonEntity person = personDao.selectOneByExample(example);
        System.out.println(555);
        System.out.println(JSON.toJSONString(person));

        return true;
    }
}
