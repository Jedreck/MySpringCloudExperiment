package com.jedreck.activitiDemo.testActiviti.meeting;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testImport {
    @Autowired
    RepositoryService repositoryService;

    /**
     * 流程部署
     */
    @Test
    public void importProcess() {
        repositoryService.createDeployment()
                .name("会议签到")
                .key("meeting")
                .addClasspathResource("processes/jinhailu.bpmn20.xml")
                .deploy();
    }

    /**
     * 流程删除
     */
    @Test
    public void deleteProcess() {
        repositoryService.deleteDeployment("1");
    }

    /**
     * 流程查询
     */
    @Test
    public void queryProcess() {
        List<Deployment> deployments =
                repositoryService.createDeploymentQuery()
                        .orderByDeploymentId()
                        .desc()
                        .list();
        for (Deployment deployment : deployments) {
            System.out.println("###############");
            System.out.println("ID:" + deployment.getId());
            System.out.println("Name:" + deployment.getName());
            System.out.println("Key:" + deployment.getKey());
        }

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .orderByDeploymentId()
                .desc()
                .list();
        for (ProcessDefinition definition : definitions) {
            System.out.println("###############");
            System.out.println("ID:" + definition.getId());
            System.out.println("Name:" + definition.getName());
            System.out.println("Key:" + definition.getKey());
            System.out.println("Version:" + definition.getVersion());
        }
    }
}
