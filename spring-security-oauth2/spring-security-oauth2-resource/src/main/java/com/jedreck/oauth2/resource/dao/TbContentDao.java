package com.jedreck.oauth2.resource.dao;

import com.jedreck.oauth2.resource.entitys.TbContent;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbContentDao extends MyMapper<TbContent> {
}