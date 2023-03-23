package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 0:26
 * @Description : 客户端的启动
 */

import com.sun.org.apache.regexp.internal.RE;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //指定线程模型
                .group(workerGroup)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                //指定IO模型
                .channel(NioSocketChannel.class)
                //IO逻辑处理,客户端相关数据的读写
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        /*
                        * 1、pipeline()返回这条连接相关逻辑的处理链，采用责任链模式。
                        * 2、addLost()添加一个逻辑处理器为的就是在客户端建立连接成功 之后，向服务端写数据
                        * */
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        //建立连接，通过.addListener监听是否连接成功，因为connect异步、返回一个future
//        bootstrap.connect("juejin.im", 80).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("连接成功！");
//            } else {
//                System.err.println("连接失败!");
//            }
//        });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    //失败重连机制函数
    private static void connect(Bootstrap bootstrap, String host, final int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.err.println("重试次数用完，放弃连接！");
            } else {
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                //本次重连的间隔，1、2、4、8秒
                int delay = 1 << order;
                System.out.println(new Date() + "连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
