package com.jedreck.oauth2.server.service;

import com.jedreck.oauth2.server.domain.entitys.TbPermission;

import java.util.List;

public interface TbPermissionService {
    List<TbPermission> selectByUserId(Long userId);
}
