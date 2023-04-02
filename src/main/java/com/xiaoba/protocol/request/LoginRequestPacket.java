package com.xiaoba.protocol.request;

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.LOGIN_REQUEST;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 11:01
 * @Description : 登录请求包
 */

@Data
public class LoginRequestPacket extends Packet {
//    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
