package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 10:13
 * @Description : 客服端向服务端写数据的逻辑处理器
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /*
    * 重写了channelActive方法编写客户端到服务端的数据逻辑
    * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端写出数据");


        //1、获取数据

        ByteBuf byteBuf = getByteBuf(ctx);

        //写数据到服务端
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "：客户端读到数据 -> "+byteBuf.toString(Charset.forName("utf-8")));
    }
    //写数据的逻辑如下：

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        //1、获取二进制抽象ByteBuf，ctx.alloc()获取到ByteBuf的内存管理器，分配一个ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //2、准备数据，将字符串的二进制数据填充到ByteBuf，指定字符串的字符集为utf-8
        byte[] bytes = "你好，小八".getBytes(Charset.forName("utf-8"));

        //3、填充数据到ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
