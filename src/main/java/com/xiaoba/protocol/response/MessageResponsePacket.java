package com.xiaoba.protocol.response;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 16:17
 * @Description : 服务端发送至客户端的消息对象
 */

import com.xiaoba.protocol.Packet;
import lombok.Data;

import static com.xiaoba.protocol.command.Command.MESSAGE_RESPONSE;
@Data
public class MessageResponsePacket extends Packet {

    private String message;


    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
