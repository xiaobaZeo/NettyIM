package com.xiaoba.server.handler;

import com.xiaoba.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        * 如果已经经过权限认证，就会直接调用remove方法删除自身，这里的this就是AuthHandler这个对象，删除之后，这条客户端链接的逻辑链就不会再有这段逻辑了.
        * */
        if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接验证完毕，无需再次验证，AuthHandler移除");
        } else {
            System.out.println("无法登录验证，强制关闭连接");
        }
    }
}