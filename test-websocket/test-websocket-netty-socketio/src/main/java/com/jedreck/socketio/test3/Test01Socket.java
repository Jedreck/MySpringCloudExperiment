package com.jedreck.socketio.test3;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class Test01Socket extends BaseSocketIOService{
    /**
     * 获取连接时的监听器
     *
     * @param socketIOClient
     */
    @Override
    protected ConnectListener getConnectListener(SocketIOClient socketIOClient) {

        return null;
    }

    /**
     * 获取断开连接时的监听器
     *
     * @param socketIOClient
     */
    @Override
    protected DisconnectListener getDisconnectListener(SocketIOClient socketIOClient) {
        return null;
    }

    /**
     * 注册事件监听器
     *
     * @param socketIOServer
     */
    @Override
    protected void addEventListener(SocketIOServer socketIOServer) {

    }

    /**
     * 设置该socket管理的事件名称
     */
    @Override
    protected String setEventName() {
        return "一个事件";
    }
}
