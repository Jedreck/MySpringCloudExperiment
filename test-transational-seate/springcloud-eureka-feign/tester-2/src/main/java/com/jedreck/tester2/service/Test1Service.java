package com.jedreck.tester2.service;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.jedreck.tester2.dao.PersonDao;
import com.jedreck.tester2.entitys.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Test1Service {
    @Autowired
    private PersonDao personDao;

    //    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public boolean changeName(PersonEntity person) {
        // 调用其他服务修改数据库
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(person.getId());
        personEntity.setName("真不知" + RandomUtil.randomNumbers(2));
        personDao.updateByPrimaryKeySelective(personEntity);

        person = personDao.selectByPrimaryKey(person.getId());
        System.out.println(333);
        System.out.println(JSON.toJSONString(person));

        if (RandomUtil.randomBoolean()) {
            System.out.println("抛出异常");
            throw new RuntimeException("异常");
        }

        return true;
    }

    @Transactional
    public boolean insertPerson(PersonEntity person) {
        // 调用其他服务修改数据库
        personDao.updateByPrimaryKeySelective(person);

        person = personDao.selectByPrimaryKey(person.getId());
        System.out.println(444);
        System.out.println(JSON.toJSONString(person));

        if (RandomUtil.randomBoolean()) {
            System.out.println("抛出异常");
            throw new RuntimeException("异常");
        }

        return true;
    }
}
