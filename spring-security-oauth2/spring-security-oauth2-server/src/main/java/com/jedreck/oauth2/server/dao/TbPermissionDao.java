package com.jedreck.oauth2.server.dao;

import com.jedreck.oauth2.server.domain.entitys.TbPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

@Repository
public interface TbPermissionDao extends MyMapper<TbPermission> {
    List<TbPermission> getPermissionByUserId(@Param("userId") Long userId);
}
