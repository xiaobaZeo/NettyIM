package com.xiaoba.server.handler;

/*
 * @Author:xiaoba
 * @Date : 2023/3/25 12:32
 * @Description :
 */

import com.xiaoba.protocol.request.LoginRequestPacket;
import com.xiaoba.protocol.response.LoginResponsePacket;
import com.xiaoba.session.Session;
import com.xiaoba.util.LoginUtil;
import com.xiaoba.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;
@ChannelHandler.Sharable

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println(new Date() + ": 登录成功!");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
//            LoginUtil.markAsLogin(ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
