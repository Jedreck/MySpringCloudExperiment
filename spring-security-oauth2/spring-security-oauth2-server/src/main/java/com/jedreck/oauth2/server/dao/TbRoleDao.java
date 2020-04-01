package com.jedreck.oauth2.server.dao;


import com.jedreck.oauth2.server.domain.entitys.TbRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbRoleDao extends MyMapper<TbRole> {
}