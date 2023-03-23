package com.xiaoba.server;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 9:54
 * @Description : 服务端处理登录请求处理器
 */

import com.xiaoba.protocol.Packet;
import com.xiaoba.protocol.PacketCodeC;
import com.xiaoba.protocol.request.LoginRequestPacket;
import com.xiaoba.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        //服务端处理登录响应，根据是不是loginRequestPacket类型来判断是否是登录请求数据包
        if (valid(loginRequestPacket)) {

            //登录校验
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + "登录成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            System.out.println(new Date() + "登录失败！");
        }
        //登录响应并编码返回
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
