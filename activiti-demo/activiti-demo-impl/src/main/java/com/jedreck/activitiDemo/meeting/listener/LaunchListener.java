package com.jedreck.activitiDemo.meeting.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.Arrays;

public class LaunchListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        execution.setVariable("签到人们", Arrays.asList("strings", "integer"));
    }
}
