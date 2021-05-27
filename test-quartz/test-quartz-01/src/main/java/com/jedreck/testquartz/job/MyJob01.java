package com.jedreck.testquartz.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;

@Slf4j
// 不允许JobDetail同时有两个相同执行Job
@DisallowConcurrentExecution
// 要传递参数就必须要有这个，不过还是推荐放在redis或落地
@PersistJobDataAfterExecution
public class MyJob01 implements Job {
    public static final String LAST_RUN_TIME = "上次执行时间";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String now = DateUtil.now();
        log.info("-------------------------");
        log.info("现在时间：{}", now);

        JobKey key = context.getJobDetail().getKey();
        log.info(key.getGroup() + "-" + key.getName());

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        dataMap.forEach((k, v) -> log.info(k + " : " + v));
        dataMap.put(LAST_RUN_TIME, now);


        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        mergedJobDataMap.forEach((k, v) -> log.info(k + " : " + v));
        mergedJobDataMap.put(LAST_RUN_TIME, now);

    }
}
