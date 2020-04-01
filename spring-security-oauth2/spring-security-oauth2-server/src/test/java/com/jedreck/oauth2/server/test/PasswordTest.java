package com.jedreck.oauth2.server.test;

import com.jedreck.oauth2.server.config.AuthorizationServerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PasswordTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    AuthorizationServerConfiguration authorizationServerConfiguration;

    @Test
    public void test1() {
        System.out.println(dataSource == authorizationServerConfiguration.dataSource());
    }
}
