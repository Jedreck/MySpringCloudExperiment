package com.jedreck.oauth2.server.dao;

import com.jedreck.oauth2.server.domain.entitys.TbRolePermission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbRolePermissionDao extends MyMapper<TbRolePermission> {
}