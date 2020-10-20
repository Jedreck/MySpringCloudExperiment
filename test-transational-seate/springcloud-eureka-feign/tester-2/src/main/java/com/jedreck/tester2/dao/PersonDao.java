package com.jedreck.tester2.dao;

import com.jedreck.tester2.entitys.PersonEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface PersonDao extends MyMapper<PersonEntity> {
}
