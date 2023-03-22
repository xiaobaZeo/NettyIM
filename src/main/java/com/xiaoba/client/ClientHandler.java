package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 22:28
 * @Description : 客户端数据请求逻辑处理器
 */

import com.xiaoba.protocol.Packet;
import com.xiaoba.protocol.PacketCodeC;
import com.xiaoba.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登录");
        //创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("xiaoba");
        loginRequestPacket.setPassword("pwd");
        //编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginRequestPacket);
    }
}
