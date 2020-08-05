package com.jedreck.mailDemoImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testSend {
    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void sendSimpleMail() {

        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件主题
        message.setSubject("这是一封测试邮件");
        //设置邮件发送者
        message.setFrom("302720001@qq.com");
        //设置邮件接收者，可以有多个接收者
        message.setTo("a302720001@outlook.com");
        //设置邮件抄送人，可以有多个抄送人
        message.setCc("a302720001@gmail.com");
        //设置隐秘抄送人，可以有多个
        message.setBcc("lanjun@enn.cn");
        //设置邮件发送日期
        message.setSentDate(new Date());
        //设置邮件的正文
        message.setText("这是测试邮件的正文");

        javaMailSender.send(message);
    }

}
