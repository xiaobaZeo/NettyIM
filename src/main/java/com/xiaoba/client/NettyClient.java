package com.xiaoba.client;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 0:26
 * @Description : 客户端的启动
 */

import com.xiaoba.client.console.ConsoleCommandManager;
import com.xiaoba.client.console.LoginConsoleCommand;
import com.xiaoba.client.handler.CreateGroupResponseHandler;
import com.xiaoba.client.handler.LoginResponseHandler;
import com.xiaoba.client.handler.LogoutResponseHandler;
import com.xiaoba.client.handler.MessageResponseHandler;
import com.xiaoba.codec.PacketDecoder;
import com.xiaoba.codec.PacketEncoder;
import com.xiaoba.codec.Spliter;
import com.xiaoba.protocol.PacketCodeC;
import com.xiaoba.protocol.request.LoginRequestPacket;
import com.xiaoba.protocol.request.MessageRequestPacket;
import com.xiaoba.util.LoginUtil;
import com.xiaoba.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Date;
import java.util.Scanner;
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
//                        ch.pipeline().addLast(new ClientHandler());
                        /*
                         * 第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的长度。
                         * */
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
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
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                //连接成功启动控制台线程
                startConsoleThread(channel);
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

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() -> {
            while (!Thread.interrupted()) {
//                if (!SessionUtil.hasLogin(channel)) {
//                    System.out.println("输入用户名登录");
//                    String userName = sc.nextLine();
//                    loginRequestPacket.setUsername(userName);
//
//                    //暂时默认
//                    loginRequestPacket.setPassword("pwd");
//
//                    //发送登录数据包
//                    channel.writeAndFlush(loginRequestPacket);
//                    waitForLoginResponse();
//                } else {
//                    String toUserId = sc.next();
//                    String message = sc.next();
//                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
//                }
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(sc, channel);
                } else {
                    consoleCommandManager.exec(sc, channel);
                }
            }
        }).start();

//        new Thread(() -> {
//            while (!Thread.interrupted()) {
////                if (LoginUtil.hasLogin(channel)) {
//                    System.out.println("输入消息发送至服务端: ");
//                    Scanner sc = new Scanner(System.in);
//                    String line = sc.nextLine();
//
////                    MessageRequestPacket packet = new MessageRequestPacket();
////                    packet.setMessage(line);
////                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
//
//                    channel.writeAndFlush(new MessageRequestPacket(line));
////                }
//            }
//        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }
}
