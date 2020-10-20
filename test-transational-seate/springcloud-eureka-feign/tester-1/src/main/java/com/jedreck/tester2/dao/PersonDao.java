package com.jedreck.tester2.dao;

import com.jedreck.tester2.entitys.PersonEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends MyMapper<PersonEntity> {
    PersonEntity getById(@Param("id") Long id);
}
