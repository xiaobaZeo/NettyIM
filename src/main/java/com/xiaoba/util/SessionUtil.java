package com.xiaoba.util;

/*
 * @Author:xiaoba
 * @Date : 2023/4/1 21:12
 * @Description : Session工具类
 */

import com.xiaoba.attribute.Attributes;
import com.xiaoba.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();
    //保存用户的会话信息
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);

        //给channel附上一个属性，这个属性就是当前用户的Session
        channel.attr(Attributes.SESSION).set(session);
    }

    //用户下线之后自动删除userId到channel的映射关系

    public static void unBindSession(Channel channel) {
//        if (hasLogin(channel)) {
//            userIdChannelMap.remove(getSession(channel).getUserId());
//            channel.attr(Attributes.SESSION).set(null);
//        }
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录!");
        }
    }

    //判断当前是否有用户的会话信息.

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    //通过设置好的对应的channel属性拿到channel对应的会话信息

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    //通过userId拿到对应的channel

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }
}
