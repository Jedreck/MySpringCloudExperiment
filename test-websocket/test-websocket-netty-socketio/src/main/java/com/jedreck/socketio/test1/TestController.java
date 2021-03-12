package com.jedreck.socketio.test1;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

//@RestController
//@RequestMapping("/push")
public class TestController {
    @Resource
    private ClientCache clientCache;

    @GetMapping("/message")
    public String pushTuUser(@RequestParam("id") String id) {
        Map<UUID, SocketIOClient> userClient = clientCache.getUserClient(String.valueOf(id));
        userClient.forEach((uuid, socketIOClient) -> {
            //向客户端推送消息
            socketIOClient.sendEvent("chatEvent", "服务端推送消息");
        });
        return "success";
    }
}
