package com.jedreck.mysqltest.service;


import cn.hutool.core.util.RandomUtil;
import com.jedreck.mysqltest.dao.PersonDao;
import com.jedreck.mysqltest.entitys.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MysqlTestService {
    @Autowired
    PersonDao personDao;

    @Transactional
    public void insert() {
        PersonEntity person = new PersonEntity();
        person.setName("zhangsan");
        person.setHeight(RandomUtil.randomInt());

        personDao.insertSelective(person);

        log.info(person.toString());

        throw new RuntimeException("有点错误。。。");
    }

    @Transactional
    public void insert2() {
        PersonEntity person = new PersonEntity();
        person.setName("lisi");
        person.setHeight(RandomUtil.randomInt());

        personDao.insertSelective(person);

        log.info(person.toString());
    }
}
