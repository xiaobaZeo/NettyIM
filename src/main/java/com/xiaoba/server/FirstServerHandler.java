package com.xiaoba.server;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 10:29
 * @Description : 服务端处理读取客户端传来的数据
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //强转msg成ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        //byteBuf.toString()就能拿到客户端传输过来的数据
        System.out.println(new Date() + "：服务端读数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "hello,xiaoba".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }
}
