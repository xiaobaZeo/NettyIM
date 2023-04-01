package com.xiaoba.protocol.request;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 16:15
 * @Description : 客户端发送消息至服务端
 */

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.MESSAGE_REQUEST;
@Data
public class MessageRequestPacket extends Packet {
    private  String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }
    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
