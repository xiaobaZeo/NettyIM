package com.xiaoba.client.handler;

/*
 * @Author:xiaoba
 * @Date : 2023/3/25 12:34
 * @Description :
 */

import com.xiaoba.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
//        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + "->" + messageResponsePacket.getMessage());
    }
}
