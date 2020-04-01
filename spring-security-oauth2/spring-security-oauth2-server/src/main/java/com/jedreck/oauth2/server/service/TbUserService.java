package com.jedreck.oauth2.server.service;

import com.jedreck.oauth2.server.domain.entitys.TbUser;

public interface TbUserService {
    TbUser getByName(String name);
}
