package com.jedreck.oauth2.server.dao;

import com.jedreck.oauth2.server.domain.entitys.TbUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbUserDao extends MyMapper<TbUser> {
}