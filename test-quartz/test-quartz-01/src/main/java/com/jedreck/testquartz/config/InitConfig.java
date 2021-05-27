package com.jedreck.testquartz.config;

import com.jedreck.testquartz.job.MyJob01;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
@Order(1)
public class InitConfig implements CommandLineRunner {
    @Autowired
    private QuartzConfig quartzConfig;

    @Override
    public void run(String... args) throws Exception {
        // 一个trigger只能占有一个job，一个job可以属于多个trigger
        SimpleTrigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1", "tGroup1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .repeatForever()
                ).build();

        JobDetail jobDetail1 = JobBuilder.newJob(MyJob01.class)
                .withIdentity("job1", "jGroup1")
                .usingJobData(MyJob01.LAST_RUN_TIME,"空")
                .build();
        quartzConfig.addJob(trigger1, jobDetail1);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("job2", "tGroup1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/15 * * * * ?")
                                .inTimeZone(TimeZone.getDefault())
                )
                .build();
        JobDetail jobDetail2 = JobBuilder.newJob(MyJob01.class)
                .withIdentity("job2", "jGroup1")
                .build();
        jobDetail2.getJobDataMap().put(MyJob01.LAST_RUN_TIME,"空");
        quartzConfig.addJob(cronTrigger, jobDetail2);
    }

}
