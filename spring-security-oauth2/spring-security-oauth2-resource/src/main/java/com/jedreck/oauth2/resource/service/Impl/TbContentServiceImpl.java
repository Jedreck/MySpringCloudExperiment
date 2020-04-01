package com.jedreck.oauth2.resource.service.Impl;

import com.jedreck.oauth2.resource.dao.TbContentDao;
import com.jedreck.oauth2.resource.entitys.TbContent;
import com.jedreck.oauth2.resource.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Autowired
    private TbContentDao tbContentDao;

    @Override
    public List<TbContent> selectAll() {
        return tbContentDao.selectAll();
    }

    @Override
    public TbContent getById(Long id) {
        return tbContentDao.selectByPrimaryKey(id);
    }

    @Override
    public int insert(TbContent tbContent) {
        return tbContentDao.insert(tbContent);
    }

    @Override
    public int update(TbContent tbContent) {
        return tbContentDao.updateByPrimaryKey(tbContent);
    }

    @Override
    public int delete(Long id) {
        return tbContentDao.deleteByPrimaryKey(id);
    }
}
