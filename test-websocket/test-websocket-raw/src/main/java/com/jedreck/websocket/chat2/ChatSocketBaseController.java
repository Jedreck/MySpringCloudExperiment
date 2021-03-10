package com.jedreck.websocket.chat2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat2")
public class ChatSocketBaseController {

    @RequestMapping("send")
    public String send(@RequestParam("userId") String userId, @RequestParam("msg") String msg) {
        ChatSocketBase.sendInfo(userId, msg);
        return "true";
    }

    @RequestMapping("sendAll")
    public String sendAll(@RequestParam("msg") String msg) {
        ChatSocketBase.sendInfoAll(msg);
        return "true";
    }

}
