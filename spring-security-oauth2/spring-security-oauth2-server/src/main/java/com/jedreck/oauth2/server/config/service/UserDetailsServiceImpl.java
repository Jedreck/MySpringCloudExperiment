package com.jedreck.oauth2.server.config.service;

import cn.hutool.core.bean.BeanUtil;
import com.jedreck.oauth2.server.domain.entitys.TbPermission;
import com.jedreck.oauth2.server.domain.entitys.TbUser;
import com.jedreck.oauth2.server.service.TbPermissionService;
import com.jedreck.oauth2.server.service.TbUserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbPermissionService tbPermissionService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        TbUser tbUser = tbUserService.getByName(userName);
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (BeanUtil.isNotEmpty(tbUser)) {
            List<TbPermission> tbPermissions = tbPermissionService.selectByUserId(tbUser.getId());
            tbPermissions.forEach(t -> {
                GrantedAuthority GrantedAuthority = new SimpleGrantedAuthority(t.getEnname());
                grantedAuthorities.add(GrantedAuthority);
            });
        }
        return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
    }
}
