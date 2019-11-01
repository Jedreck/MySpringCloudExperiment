package com.jedreck.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 轮询机制
 * 每个服务调用total次后，才能调用下一个服务，total默认是2
 *
 * @author LanJun
 * 2019/11/1 12:30
 */
public class FirstRule extends AbstractLoadBalancerRule {

    private static final int DEFAULT_LOOP_TIMES = 2;
    private int total = 0;
    private int loopTimes;
    private int index;

    public FirstRule() {
        this.loopTimes = DEFAULT_LOOP_TIMES;
    }

    public FirstRule(int loopTimes) {
        this.loopTimes = (loopTimes <= 0) ? DEFAULT_LOOP_TIMES : loopTimes;
    }

    private Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        while (server == null) {
            //前期判断
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            //判断循环的次数
            if (total < loopTimes) {
                server = upList.get(index);
                total++;
            } else {
                total = 0;
                index++;
                if (index >= upList.size()) {
                    index = 0;
                }
            }

            if (server == null) {
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return server;
            }

            server = null;
            Thread.yield();
        }
        return server;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
}
