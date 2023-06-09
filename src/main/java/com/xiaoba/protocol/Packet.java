package com.xiaoba.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 10:56
 * @Description : 定义协议版本和获取指令的抽象类
 */
@Data
public abstract class Packet {
    /*
    * 协议版本
    *
    * */

    @JSONField(deserialize = false, serialize = false)
    private byte version = 1;

    /*
    * 获取指令
    * 所有的指令数据包都必须实现这个方法，这样我们就可以知道某种指令的含义。
    * */

    @JSONField(serialize = false)
    public abstract  Byte getCommand();
}
