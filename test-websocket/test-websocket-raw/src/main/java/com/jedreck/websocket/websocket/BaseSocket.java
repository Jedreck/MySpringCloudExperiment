package com.jedreck.websocket.websocket;

import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class BaseSocket {
    /**
     * 连接记录池
     */
    private static final ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 普通常量
     */
    public static final String INFO_SEND_ERROR = "发送消息失败";
    public static final String INFO_CONNECT_SUCCESS = "连接成功";
    public static final String INFO_CLOSE_ERROR = "关闭session异常";

    @OnOpen
    public void onOpen(Session session) {
        webSocketMap.remove(session.getId());
        webSocketMap.put(session.getId(), session);
        log.info(INFO_CONNECT_SUCCESS);
    }

    @OnClose
    public void onClose(Session session) {
        try {
            webSocketMap.remove(session.getId());
            session.close();
        } catch (Exception e) {
            log.error(INFO_CLOSE_ERROR, e);
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.error("Socket Session 出现错误，sessionId : {}", session.getId(), t);
    }

    /**
     * 向全体发送
     * 只开放给继承类，不能直接调用，防止误操作
     */
    protected static void sendInfoAll(String message) {
        try {
            webSocketMap.forEach((k, v) -> sendInfo(v, message));
        } catch (Exception e) {
            log.error(INFO_SEND_ERROR, e);
        }
    }

    /**
     * 发送消息
     */
    public static void sendInfo(String sessionId, String message) {
        try {
            Session session = webSocketMap.get(sessionId);
            BaseSocket.sendInfo(session, message);
        } catch (Exception e) {
            log.error(INFO_SEND_ERROR, e);
        }
    }

    public static void sendInfo(Session session, String message) {
        try {
            if (null == session) {
                return;
            }
            session.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            log.error(INFO_SEND_ERROR, e);
        }
    }

    /**
     * 发送对象
     */
    public static void sendObj(String sessionId, Message msgObj) {
        BaseSocket.sendObj(webSocketMap.get(sessionId), msgObj);
    }

    public static void sendObj(Session session, Message msgObj) {
        try {
            if (null == session) {
                return;
            }
            log.debug("向客户端{}发送数据：{}", session.getId(), msgObj.toString());
            session.getAsyncRemote().sendObject(msgObj);
        } catch (Exception e) {
            log.error(INFO_SEND_ERROR, e);
        }

    }

    /**
     * 发送主题消息
     */
    public static void sendToTopic(String sessionId, String topic, Object data) {
        BaseSocket.sendToTopic(webSocketMap.get(sessionId), topic, data);
    }

    public static void sendToTopic(Session session, String topic, Object data) {
        if (null == session) {
            return;
        }
        Message message = Message.builder()
                .topic(topic)
                .data(data)
                .build();
        BaseSocket.sendObj(session, message);
    }

    public static boolean isSessionExist(String sessionId) {
        return webSocketMap.containsKey(sessionId);
    }

    public abstract void onMessage(Session session, String message);

    /**
     * 封装一个
     */
    @Builder
    @Data
    static class Message {

        /**
         * 消息类型
         */
        private String topic;
        /**
         * 消息数据
         */
        private Object data;

        @Override
        public String toString() {
            return JSONUtil.toJsonStr(this);
        }
    }
}
