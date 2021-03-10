package com.jedreck.websocket.chat2;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.jedreck.websocket.websocket.BaseSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/chat2")
public class ChatSocketBase extends BaseSocket {
    /**
     * 用户id -> session id
     */
    private static final ConcurrentHashMap<String, String> userMap = new ConcurrentHashMap<>();

    private String userId;

    private static final String USER_ID = "userId";

    @Override
    @OnOpen
    public void onOpen(Session session) {
        super.onOpen(session);
        String queryString = session.getQueryString();
        HashMap<String, String> paramMap = HttpUtil.decodeParamMap(queryString, StandardCharsets.UTF_8.name());
        userId = Optional.of(paramMap).map(m -> m.get(USER_ID)).orElse(null);
        if (StrUtil.isBlank(userId)) {
            onClose(session);
        }
        userMap.put(userId, session.getId());
        log.info(INFO_CONNECT_SUCCESS + " userId：" + userId);
    }

    @Override
    @OnClose
    public void onClose(Session session) {
        super.onClose(session);
        userMap.remove(userId);
    }

    @Override
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info(message);
        sendInfoAll(message);
    }

    public static void sendInfo(String userId, String message) {
        try {
            BaseSocket.sendInfo(userMap.get(userId), message);
        } catch (Exception e) {
            log.error(INFO_SEND_ERROR, e);
        }
    }

    public static void sendInfoAll(String message) {
        BaseSocket.sendInfoAll(message);
    }
}
