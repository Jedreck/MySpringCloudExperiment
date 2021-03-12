package com.jedreck.socketio.test2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    /**
     * 登录用户编号
     */
    private String loginUserNum;

    /**
     * 推送内容
     */
    private String content;

    // Other Detail Property...
}

