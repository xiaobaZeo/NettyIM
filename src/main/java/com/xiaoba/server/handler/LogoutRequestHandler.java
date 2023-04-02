package com.xiaoba.server.handler;

/*
 * @Author:xiaoba
 * @Date : 2023/4/2 14:26
 * @Description :
 */

import com.xiaoba.protocol.request.LogoutRequestPacket;
import com.xiaoba.protocol.response.LogoutResponsePacket;
import com.xiaoba.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
