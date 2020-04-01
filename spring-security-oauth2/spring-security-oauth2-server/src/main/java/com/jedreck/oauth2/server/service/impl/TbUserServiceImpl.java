package com.jedreck.oauth2.server.service.impl;

import com.jedreck.oauth2.server.dao.TbUserDao;
import com.jedreck.oauth2.server.domain.entitys.TbUser;
import com.jedreck.oauth2.server.service.TbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserDao tbUserDao;

    @Override
    public TbUser getByName(String name) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
            throw new NullPointerException("用户名为空");
        }
        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        TbUser tbUser = null;
        try {
            tbUser = tbUserDao.selectOneByExample(example);
        } catch (Exception e) {
            log.error("有问题");
        }
        return tbUser;
    }
}
