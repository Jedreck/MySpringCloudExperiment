package com.jedreck.socketio.test3;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Test01Socket extends BaseSocketIOService {

    /**
     * 获取连接时的监听器
     *
     * @param socketIOClient
     */
    @Override
    protected void getConnectListener(SocketIOClient socketIOClient) {
        List<String> names = socketIOClient.getHandshakeData().getUrlParams().get("name");
        String name = names.get(0);
        log.info("{}--连接", name);
        clientMap.put(name, socketIOClient);
        socketIOClient.sendEvent("connect", "连接成功");
    }

    /**
     * 获取断开连接时的监听器
     *
     * @param socketIOClient
     */
    @Override
    protected void getDisconnectListener(SocketIOClient socketIOClient) {
        List<String> names = socketIOClient.getHandshakeData().getUrlParams().get("name");
        String name = names.get(0);
        log.info("{}--断开", name);
        clientMap.remove(name);
        socketIOClient.sendEvent("disconnect", "断开连接成功");
    }

    /**
     * 注册事件监听器
     *
     * @param socketIOServer
     */
    @Override
    protected void addEventListener(SocketIOServer socketIOServer) {
        socketIOServer.addEventListener(EVENT_NAME, Test01Emit.class, (socketIOClient, test01Emit, ackRequest) -> {
            List<String> names = socketIOClient.getHandshakeData().getUrlParams().get("name");
            String name = names.get(0);
            String message = test01Emit.getMessage();
            log.info("收到一条消息：");
            log.info(message);
            log.info("------------------");
            test01Emit.setFrom(name);
            sendMessage(test01Emit.getSendTo(), test01Emit);
        });
    }

    /**
     * 设置该socket管理的事件名称
     */
    @Override
    protected String setEventName() {
        return "一个事件";
    }

    @Data
    private static class Test01Emit {
        private String from;
        private String sendTo;
        private String message;
    }
}
