package com.jedreck.activitiDemo.testActiviti.meeting;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class testRuntime {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    /**
     * 发起流程
     */
    @Test
    public void startProcess() {
        Map<String, Object> variable = new HashMap<>();
        variable.put("会议发起人", "金海路领导");
        ProcessInstance process = runtimeService.startProcessInstanceByKey("jinhailu", variable);
        Task task = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).singleResult();
        System.out.println(task.getId());
        System.out.println(task.getName());
        System.out.println(task.getAssignee());
    }

    /**
     * 查询流程
     */
    @Test
    public void queryExecution() {
        List<Task> tasks = taskService.createTaskQuery().list();
        for (Task task : tasks) {
            log.info("###############");
            log.info("ID:" + task.getId());
            log.info("Name:" + task.getName());
            log.info("Assignee:" + task.getAssignee());
        }
    }

    /**
     * 完成实例
     */
    @Test
    public void completeExecution() {
        String taskId = "72526";
        taskService.complete(taskId);
        queryExecution();
    }

    /**
     * 完成实例
     */
    @Test
    public void completeExecutionV() {
        String taskId = "27507";
        Map<String, Object> map = new HashMap<>(1);
        map.put("assigneeList", "aaa,bbb");
        taskService.complete(taskId, map);
    }
}
