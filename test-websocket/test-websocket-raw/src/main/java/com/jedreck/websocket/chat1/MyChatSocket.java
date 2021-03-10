package com.jedreck.websocket.chat1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/websocket/chat/{userId}")
public class MyChatSocket {
    // 在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    // socket对象，连接池
    private static final CopyOnWriteArraySet<MyChatSocket> webSocketSet = new CopyOnWriteArraySet<>();


    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userId = null;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        // 加入连接池中
        webSocketSet.add(this);
        // 在线数加1
        onlineCount.incrementAndGet();
        log.info("有新连接{}加入！当前在线人数为 {}", userId, onlineCount.get());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.info("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        onlineCount.decrementAndGet();
        log.info("有一连接关闭！当前在线人数为" + onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自{}客户端的消息:{}", userId, message);

        //群发消息
        for (MyChatSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @OnError public void onError(Session session, Throwable error) {
     * log.info("发生错误");
     * error.printStackTrace();
     * }
     * <p>
     * <p>
     * public void sendMessage(String message) throws IOException {
     * this.session.getBasicRemote().sendText(message);
     * //this.session.getAsyncRemote().sendText(message);
     * }
     * <p>
     * <p>
     * /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) {
        for (MyChatSocket item : webSocketSet) {
            try {
                //这里可以设定只推送给这个userId的，为null则全部推送
                if (userId == null || item.userId.equals(userId)) {
                    item.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否与该用户端创建socket连接
     */
    public static boolean isUserConnected(String userId) {
        for (MyChatSocket item : webSocketSet) {
            if (item.userId.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
