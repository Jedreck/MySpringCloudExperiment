package com.jedreck.mysqltest.dao;

import com.jedreck.mysqltest.entitys.PersonEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface PersonDao extends MyMapper<PersonEntity> {
    PersonEntity getById(@Param("id") Long id);
}
