package com.xiaoba.serialize;

/*
 * @Author:xiaoba
 * @Date : 2023/3/22 11:10
 * @Description : 序列化接口：1、获取具体的序列化算法表示2、将Java对象转换成字节数组，将字节数组转换成某种类型的Java对象
 */

import com.xiaoba.serialize.impl.JSONSerializer;
import lombok.experimental.var;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();


    /*
     * 序列化算法
     *
     * */

    byte getSerializerAlgorithm();


    /*
     * java 对象转二进制
     * */
    byte[] serialize(Object object);


    /*
     * 二进制转换成java对象
     * */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
