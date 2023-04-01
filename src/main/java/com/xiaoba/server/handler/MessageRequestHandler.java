package com.xiaoba.server.handler;

/*
 * @Author:xiaoba
 * @Date : 2023/3/25 12:32
 * @Description :
 */

import com.xiaoba.protocol.request.MessageRequestPacket;
import com.xiaoba.protocol.response.MessageResponsePacket;
import com.xiaoba.session.Session;
import com.xiaoba.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        /*
         * 服务端简单处理客户端的信息实现如下
         * */
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");


        /*
         * 服务端处理客户端之间信息发送的实现如下
         * */

        //1、拿到消息发送方的会话消息
        Session session = SessionUtil.getSession(ctx.channel());

        //2、通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //3、拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //4、将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "]不在线，发送失败");
        }
    }
}
