package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 22:28
 * @Description : 客户端数据请求逻辑处理器
 */

import com.xiaoba.protocol.Packet;
import com.xiaoba.protocol.PacketCodeC;
import com.xiaoba.protocol.request.LoginRequestPacket;
import com.xiaoba.protocol.response.LoginResponsePacket;
import com.xiaoba.protocol.response.MessageResponsePacket;
import com.xiaoba.util.LoginUtil;
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
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(buffer);
    }



    /*
     * 接收服务端数据，进行逻辑处理
     * */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //拿到msg，进行解码操作
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        //如果是服务端返回的登录是否成功信息内容
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + "客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + "客户端登录失败，原因是:" + loginResponsePacket.getReason());
            }
        }
        //如果是服务端返回的消息内容
        else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + "：收到服务端信息" + messageResponsePacket.getMessage());
        }
    }
}
