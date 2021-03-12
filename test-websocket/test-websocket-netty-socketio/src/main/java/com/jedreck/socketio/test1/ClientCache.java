package com.jedreck.socketio.test1;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * kcm
 */
//@Component
public class ClientCache {

    //本地缓存
    private static final Map<String, HashMap<UUID, SocketIOClient>> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 存入本地缓存
     *
     * @param userId         用户ID
     * @param sessionId      页面sessionID
     * @param socketIOClient 页面对应的通道连接信息
     */
    public void saveClient(String userId, UUID sessionId, SocketIOClient socketIOClient) {
        if (StrUtil.isNotBlank(userId)) {
            HashMap<UUID, SocketIOClient> sessionIdClientCache = concurrentHashMap.get(userId);
            if (sessionIdClientCache == null) {
                sessionIdClientCache = new HashMap<>();
            }
            sessionIdClientCache.put(sessionId, socketIOClient);
            concurrentHashMap.put(userId, sessionIdClientCache);
        }
    }

    /**
     * 根据用户ID获取所有通道信息
     */
    public Map<UUID, SocketIOClient> getUserClient(String userId) {
        return concurrentHashMap.get(userId);
    }

    /**
     * 根据用户ID及页面sessionID删除页面链接信息
     */
    public void deleteSessionClient(String userId, UUID sessionId) {
        concurrentHashMap.get(userId).remove(sessionId);
    }
}


