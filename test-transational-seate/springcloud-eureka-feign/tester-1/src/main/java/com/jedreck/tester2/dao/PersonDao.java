package com.jedreck.tester2.dao;

import com.jedreck.tester2.entitys.PersonEntity;
import tk.mybatis.mapper.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends MyMapper<PersonEntity> {
}
