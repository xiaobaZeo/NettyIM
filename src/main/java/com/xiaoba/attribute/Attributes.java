package com.xiaoba.attribute;

/*
 * @Author:xiaoba
 * @Date : 2023/3/23 16:20
 * @Description : 是否登录成功的标志位
 */

import com.xiaoba.session.Session;
import io.netty.util.AttributeKey;
import lombok.experimental.var;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
