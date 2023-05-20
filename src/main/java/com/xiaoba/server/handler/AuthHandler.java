package com.xiaoba.server.handler;

import com.xiaoba.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
@ChannelHandler.Sharable

public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        * 如果已经经过权限认证，就会直接调用remove方法删除自身，这里的this就是AuthHandler这个对象，删除之后，这条客户端链接的逻辑链就不会再有这段逻辑了.
        *
        * 根据SessionUtil工具类来权鉴、而不是LoginUtil,否则将导致无法进行单独聊天
        * */
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        if (LoginUtil.hasLogin(ctx.channel())) {
//            System.out.println("当前连接验证完毕，无需再次验证，AuthHandler移除");
//        } else {
//            System.out.println("无法登录验证，强制关闭连接");
//        }
//    }
}