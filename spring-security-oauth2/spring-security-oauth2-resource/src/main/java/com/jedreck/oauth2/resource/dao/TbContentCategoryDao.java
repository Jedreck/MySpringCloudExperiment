package com.jedreck.oauth2.resource.dao;

import com.jedreck.oauth2.resource.entitys.TbContentCategory;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface TbContentCategoryDao extends MyMapper<TbContentCategory> {
}