package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 0:26
 * @Description : 客户端的启动
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //指定线程模型
                .group(workerGroup)
                //指定IO模型
                .channel(NioServerSocketChannel.class)
                //IO逻辑处理
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {

                    }
                });
        //建立连接，通过.addListener监听是否连接成功，因为connect异步、返回一个future
        bootstrap.connect("juejin.im", 80).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else {
                System.err.println("连接失败!");
            }
        });
    }
}
