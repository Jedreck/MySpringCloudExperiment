package com.jedreck.testspringcloudgateway.domain.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限实体
 */
@Data
public class AuthBean implements Serializable {
    // key
    private String key;
    //权限1
    private Integer auth01;

    //日期权限
    private Date startDate;
    private Date endDate;

    //时间权限
    private Date startTime;
    private Date endTime;
}
