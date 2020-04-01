package com.jedreck.oauth2.resource.service;

import com.jedreck.oauth2.resource.entitys.TbContent;

import java.util.List;

public interface TbContentService {
    List<TbContent> selectAll();

    TbContent getById(Long id);

    int insert(TbContent tbContent);

    int update(TbContent tbContent);

    int delete(Long id);
}
