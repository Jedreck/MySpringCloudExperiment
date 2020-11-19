package com.jedreck.testspringcloudgateway;

import org.junit.Test;

import java.time.LocalDate;
import java.util.TimeZone;

public class Test01 {
    @Test
    public void test01() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        LocalDate parse = LocalDate.parse("2020-11-11");
        System.out.println(parse.getYear());
    }
}
