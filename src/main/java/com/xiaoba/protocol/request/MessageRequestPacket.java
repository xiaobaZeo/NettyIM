package com.xiaoba.protocol.request;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 16:15
 * @Description : 客户端发送消息至服务端
 */

import com.xiaoba.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.xiaoba.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
//一定要加@NoArgsConstructor ，在类上使用，这个注解可以生成无参构造方法，不然客户端发消息，会直接报错
public class MessageRequestPacket extends Packet {
    private String message;
    private String toUserId;

//    public MessageRequestPacket(String message) {
//        this.message = message;
//    }
    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }
    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
