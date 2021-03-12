package com.jedreck.socketio.test1;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
//@Component
public class EventListener {
    @Resource
    private ClientCache clientCache;

    /**
     * 客户端连接
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");

        UUID sessionId = client.getSessionId();
        clientCache.saveClient(userId, sessionId, client);
        log.info("建立连接");
    }

    /**
     * 客户端断开
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        if (StrUtil.isNotBlank(userId)) {
            clientCache.deleteSessionClient(userId, client.getSessionId());
            log.info("关闭连接");
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    // 暂未使用
    @OnEvent("messageevent")
    public void onEvent(SocketIOClient client, AckRequest request) {
    }
}

