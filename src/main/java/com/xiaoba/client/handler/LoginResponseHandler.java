package com.xiaoba.client.handler;

/*
 * @Author:xiaoba
 * @Date : 2023/3/25 12:34
 * @Description :
 */

import com.xiaoba.protocol.request.LoginRequestPacket;
import com.xiaoba.protocol.response.LoginResponsePacket;
import com.xiaoba.session.Session;
import com.xiaoba.util.LoginUtil;
import com.xiaoba.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

//SimpleChannelInboundHandler类型判断和对象传递
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("xiaoba");
//        loginRequestPacket.setPassword("pwd");
//
//        // 写数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
//        if (loginResponsePacket.isSuccess()) {
//            System.out.println(new Date() + ": 客户端登录成功");
//            LoginUtil.markAsLogin(ctx.channel());
//        } else {
//            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
//        }
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}