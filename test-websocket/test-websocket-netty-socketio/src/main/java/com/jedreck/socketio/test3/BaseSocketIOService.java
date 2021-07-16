package com.jedreck.socketio.test3;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SocketIO 抽象类
 */
@Slf4j
public abstract class BaseSocketIOService {

    /**
     * 已连接的客户端
     * 推送时用来获取对应客户会话信息
     */
    protected final Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    protected String EVENT_NAME;

    @Autowired
    private SocketIOServer socketServer;

    /**
     * 获取连接时的监听器
     */
    protected abstract ConnectListener getConnectListener(SocketIOClient socketIOClient);

    /**
     * 获取断开连接时的监听器
     */
    protected abstract DisconnectListener getDisconnectListener(SocketIOClient socketIOClient);

    /**
     * 注册事件监听器
     */
    protected abstract void addEventListener(SocketIOServer socketIOServer);

    /**
     * 设置该socket管理的事件名称
     */
    protected abstract String setEventName();

    /**
     * 给客户端发送消息的方法
     *
     * @param uuid 客户端的key，用于区别客户端
     * @param data 数据对象
     */
    protected void sendMessage(String uuid, Object data) {
        if (StrUtil.isBlank(uuid)) {
            return;
        }
        SocketIOClient client = this.clientMap.get(uuid);
        if (client != null) {
            client.sendEvent(EVENT_NAME, data);
        }
    }

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    private void autoStartup() {
        start();
    }

    /**
     * 被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
     * 被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前。
     */
    @PreDestroy
    private void autoStop() {
        stop();
    }

    private void start() {
        // 设置事件名称
        EVENT_NAME = setEventName();
        // 注册连接监听器
        // 客户端连接请求的监听器，service会循环调用所有注册的监听器，可以在监听器操作中进行过滤
        socketServer.addConnectListener(this::getConnectListener);

        // 监听客户端断开连接
        socketServer.addDisconnectListener(this::getDisconnectListener);

        // 处理自定义的事件，与连接监听类似
        this.addEventListener(socketServer);
    }

    private void stop() {
        if (socketServer != null) {
            socketServer.stop();
            socketServer = null;
        }
    }
}
