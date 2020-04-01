package com.jedreck.oauth2.server.dao;

import com.jedreck.oauth2.server.domain.entitys.TbUserRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbUserRoleDao extends MyMapper<TbUserRole> {
}