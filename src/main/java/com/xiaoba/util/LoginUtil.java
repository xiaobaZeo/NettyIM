package com.xiaoba.util;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 16:33
 * @Description : 设置标位位以及判断是否有标志位，若有标志位就登陆成功，就可以实现控制台输入消息并发送至服务端
 */

import com.xiaoba.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
