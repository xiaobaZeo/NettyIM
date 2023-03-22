package com.xiaoba.server;

/*
 * @Author:xiaoba
 * @Date : 2023/3/21 23:32
 * @Description : 服务端启动流程
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        //监听端口，接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理每条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //引导类，引导服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /*
         * .group()配置两大线程组
         * .channel()指定NIO模型，还有BIO等模型可以选择
         * .childHandler()创建ChannelInitializer对象并在此对象中定义每条连接的数据读写，其中NioSocketChannel和NioServerSocketChannel是对NIO类型的连接抽象。
         * .handler()用于指定在服务端启动过程中的一些逻辑
         * */
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) {
                ch.pipeline().addLast(new FirstServerHandler());
            }
        });
        /*
         * serverBootstrap.bind()是异步方法，调用后立即返回一个ChannelFuture，可以给这个ChannerlFuture加一个监听器GenericFutureListener,最后在监听器下的operationComplete()监听端口是否绑定成功。
         * */
//        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
//            @Override
//            public void operationComplete(Future<? super Void> future) throws Exception {
//                if (future.isSuccess()) {
//                    System.out.println("端口绑定成功");
//                } else {
//                    System.out.println("端口绑定失败");
//                }
//            }
//        });
        bind(serverBootstrap, 8000);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口" + port + "绑定成功");
                } else {
                    System.err.println("端口" + port + "绑定失败");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
